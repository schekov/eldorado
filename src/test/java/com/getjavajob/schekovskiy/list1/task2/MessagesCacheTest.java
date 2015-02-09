package com.getjavajob.schekovskiy.list1.task2;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class MessagesCacheTest {

    private static final int TEST_TIME_TO_LIVE = 2000; // milliseconds
    private static final String msg1 = "testmsg1";
    private static final String msg2 = "testmsg2";
    private static final String msg3 = "testmsg2";
    Cache cache;


    @Before
    public void setUp() {
        cache = new Cache(TEST_TIME_TO_LIVE);
    }


    @Test
    public void testPut() {
        boolean isInCacheMsg1 = cache.put(msg1, msg1);
        boolean isInCacheMsg2 = cache.put(msg2, msg2);
        boolean isInCacheMsg3 = cache.put(msg3, msg3);
        assertFalse(isInCacheMsg1);
        assertFalse(isInCacheMsg2);
        assertTrue(isInCacheMsg3);

    }

    @Test
    public void testGet() {
        cache.put(msg1, msg1);
        cache.put(msg2, msg2);

        assertTrue(cache.get(msg1).equals(msg1));
        assertTrue(cache.get(msg2).equals(msg2));

        try {
            TimeUnit.MILLISECONDS.sleep(TEST_TIME_TO_LIVE + 3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            cache.get(msg1);
        } catch (NullPointerException e) {
            System.out.println("Get test ok");
        }
    }
}
