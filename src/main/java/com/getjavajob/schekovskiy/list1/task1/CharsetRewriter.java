package com.getjavajob.schekovskiy.list1.task1;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class CharsetRewriter {

    private static final long DEFAULT_LIMIT_FOR_GZIP = 10_000_000;
    private static final String DEFAULT_OUTPUT_CHARSET = "UTF8";
    private static final String DEFAULT_INPUT_FILE_CHARSET = "CP1251";

    private String filePath;
    private long limitForGZIP;
    private String outCharset;
    private String inputCharset;

    public CharsetRewriter() {
        this(DEFAULT_INPUT_FILE_CHARSET, DEFAULT_OUTPUT_CHARSET, DEFAULT_LIMIT_FOR_GZIP);
    }

    public CharsetRewriter(String inputCharset, String outCharset, long limitForGZIPCompression) {
        this.outCharset = outCharset;
        this.inputCharset = inputCharset;
        limitForGZIP = limitForGZIPCompression;

    }

    public String getOutCharset() {
        return outCharset;
    }

    public void setOutCharset(String outCharset) {
        this.outCharset = outCharset;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getLimitForGZIP() {
        return limitForGZIP;
    }

    public void setLimitForGZIP(long limitForGZIP) {
        this.limitForGZIP = limitForGZIP;
    }

    public void changeCharset(String filePath) throws IOException {
        File file = new File(filePath);
        final String fileNameWithExt = file.getName();
        final String fileDir = file.getParent();

        int pos = fileNameWithExt.lastIndexOf('.');
        final String fileNameWithoutExt = fileNameWithExt.substring(0, pos);
        final String fileExt = fileNameWithExt.substring(pos);

        File rewrittenFile = new File(fileDir, fileNameWithoutExt + outCharset + fileExt);
        rewrittenFile.createNewFile();

        try (
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = new FileOutputStream(rewrittenFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, inputCharset));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, outCharset))

        ) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            if (rewrittenFile.length() > limitForGZIP) {
                File compressedFile = new File(fileDir, fileNameWithoutExt + outCharset + ".gz");
                compressToGZIP(rewrittenFile, compressedFile);
            }
        }
    }

    public void compressToGZIP(File file, File fileForGZIP) throws IOException {
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(fileForGZIP))
        ) {
            byte[] buffer = new byte[2048];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                gzipOutputStream.write(buffer, 0, len);
            }
            file.deleteOnExit();
        }
    }
}