package com.getjavajob.schekovskiy.list1.task4;

import java.io.IOException;

public class ParseURL {

    private static final String TEST_LINK = "https://www.oracle.com/java/index.html";

    public static void main(String[] args) throws IOException {
        SimplePageParser pageParser = new SimplePageParser("src/main/resources/");
        pageParser.parsePage(TEST_LINK);

    }
}

