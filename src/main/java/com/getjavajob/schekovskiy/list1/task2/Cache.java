package com.getjavajob.schekovskiy.list1.task2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache<K, V> {

    private static final long DEFAULT_MILLIS_UNTIL_EXPIRATION = 60 * 60 * 1000; // 60 minutes
    private long millisUntilExpiration = DEFAULT_MILLIS_UNTIL_EXPIRATION;
    private Map<K, EntryCache> entryCacheMap = new LinkedHashMap<>();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private Thread cacheCleanerThread;

    private class EntryCache {
        private V value;
        private long timestamp;

        public EntryCache(V value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public Cache() {
    }

    public Cache(long millisUntilExpiration) {
        this.millisUntilExpiration = millisUntilExpiration;
    }

    /**
     * @return true if putting rewrote value
     */
    public boolean put(K key, V value) {
        if (cacheCleanerThread == null) {
            cacheCleanerThread = new Thread(new Runnable() {
                public void run() {
                    cleanUp();
                }
            });
            service.scheduleAtFixedRate(cacheCleanerThread, 0, millisUntilExpiration, TimeUnit.MILLISECONDS);
        }

        EntryCache entry = entryCacheMap.get(key);
        boolean isInCache = (entry != null);

        write.lock();
        try {
            entry = new EntryCache(value, System.currentTimeMillis());
            entryCacheMap.put(key, entry);
        } finally {
            write.unlock();
        }
        return isInCache;
    }

    public V get(K key) {
        V value = entryCacheMap.get(key).getValue();
        return value;
    }

    public boolean containsKey(K key) {
        return entryCacheMap.containsKey(key);
    }

    public int size() {
        return entryCacheMap.size();
    }

    public void clear() {
        entryCacheMap.clear();
    }

    public void cleanUp() {
        for (Iterator<K> iterator = entryCacheMap.keySet().iterator(); iterator.hasNext(); ) {
            K key = iterator.next();
            EntryCache e = entryCacheMap.get(key);
            long now = System.currentTimeMillis();
            long liveTime = e.getTimestamp() + millisUntilExpiration;
            if (liveTime < now) {
                iterator.remove();
                break;
            }
        }
    }

    private static String getCurrentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
