package com.getjavajob.schekovskiy.list1.task1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class CharsetRewriterTest {

    public static final String filePath = "src/main/resources/testFile.txt";
    public static final String bigFilePath = "src/main/resources/bigTestFile.txt";
    public static final CharsetRewriter cr = new CharsetRewriter();
    public static final File FILE = new File(filePath);
    public static final File BIG_FILE = new File(bigFilePath);

    public static final String dir = FILE.getParent();
    public static final String fileNameWithExt = FILE.getName();
    public static final int pos1 = fileNameWithExt.lastIndexOf('.');
    public static final String fileNameWithoutExt = fileNameWithExt.substring(0, pos1);

    public static final String bigFileNameWithExt = BIG_FILE.getName();
    public static final int pos2 = bigFileNameWithExt.lastIndexOf('.');
    public static final String bigFileNameWithoutExt = bigFileNameWithExt.substring(0, pos2);

    public static final File newFile = new File(dir, fileNameWithoutExt + "UTF8" + ".txt");
    public static final File newBigFile = new File(dir, bigFileNameWithoutExt + "UTF8" + ".gz");

    @Before
    public void setUp() throws IOException {
        cr.changeCharset(filePath);
        cr.changeCharset(bigFilePath);
    }

    @After
    public void tearDown() {
        newFile.delete();
        newBigFile.delete();
    }

    @Test
    public void testNewFileExists() {
        assertEquals(true, newFile.exists());
    }

    @Test
    public void testChangeCharset() throws Exception {
        String encoding;
        String expectedEncoding = "UTF8";
        try (
                InputStreamReader in = new InputStreamReader(new FileInputStream(newFile))
        ) {
            encoding = in.getEncoding();
        }
        assertEquals(expectedEncoding, encoding);
    }

    @Test
    public void testCompressToGZIP() throws Exception {
        long bigFileLength = BIG_FILE.length();
        long bigFileGZIPLength = newBigFile.length();

        if (!(bigFileGZIPLength < bigFileLength * 1.5)) {
            throw new Exception("GZIP fail!");
        }
    }


}