package com.getjavajob.schekovskiy.list1.task4;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.*;

public class SimplePageParser {

    private final static String CHARSET = "UTF8";

    private ExecutorService threadPoolExecutor;
    private String dir;
    private String rootDirectory;
    private String pagePath;

    public SimplePageParser(String dir) throws MalformedURLException, UnsupportedEncodingException {
        this.dir = dir;
        threadPoolExecutor = new ThreadPoolExecutor(3, 6, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
        );
    }

    public void parsePage(String urlString) throws IOException {
        URL url = new URL(urlString);
        rootDirectory = url.getHost();
        pagePath = URLDecoder.decode(url.getFile(), CHARSET);

        File mainPage = createDirsAndReturnFile(pagePath);
        mainPage.createNewFile();
        FileUtils.copyURLToFile(url, mainPage);
        Document doc = Jsoup.parse(mainPage, CHARSET);

        Elements links = doc.select("link[type=text/css]");
        save(links, LinkType.CSS);

        links = doc.select("img[src]");
        save(links, LinkType.IMG);
    }

    public enum LinkType {
        CSS,
        IMG
    }

    private void save(final Elements links, LinkType linkType) throws IOException {
        final boolean isCss = (linkType == LinkType.CSS);
        final boolean isImg = (linkType == LinkType.IMG);

        BlockingQueue<Runnable> runnables = new ArrayBlockingQueue<>(100);

        for (final Element link : links) {
            String attributeKey = "";
            if (isCss) attributeKey = "href";
            else if (isImg) attributeKey = "src";
            final String finalAttributeKey = attributeKey;

            Runnable task = new Runnable() {
                @Override
                public void run() {

                    String urlStringFromLink = link.attr(finalAttributeKey);
                    String path = getPathFromLink(urlStringFromLink);
                    File file = null;
                    try {
                        file = createDirsAndReturnFile(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String sub = urlStringFromLink.substring(0, 4);
                    if (!(sub.equals("http"))) {
                        urlStringFromLink = "http:" + urlStringFromLink;
                    }
                    URL urlFromLink = null;
                    try {
                        urlFromLink = new URL(urlStringFromLink);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (urlFromLink != null && file != null) {
                            FileUtils.copyURLToFile(urlFromLink, file);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            runnables.add(task);
        }
        for (Runnable runnable : runnables) {
            threadPoolExecutor.execute(runnable);
        }
    }

    private String getPathFromLink(String link) {
        String resultString;
        int extSize = 3;
        String[] temp = link.split("/");
        int index = temp.length - 1;
        String elem = temp[index];
        int extIndex = elem.lastIndexOf('.');
        resultString = "/" + elem.substring(0, extIndex + 1 + extSize);

        while (index > 0)

        {
            index--;
            elem = temp[index];
            if (elem.equals(rootDirectory) || elem.equals("..")) {
                break;
            }
            resultString = "/" + elem + resultString;
        }

        return resultString;
    }


    private File createDirsAndReturnFile(String path) throws IOException {
        String filePath = dir + rootDirectory + path;
        File file = new File(filePath);

        int pos = filePath.lastIndexOf('/') + 1;
        String fileDirectories = filePath.substring(0, pos);
        File directories = new File(fileDirectories);
        directories.mkdirs();

        return file;
    }
}

