package org.dylan.threadlocal;

import java.text.SimpleDateFormat;

/**
 * @author Dylan
 * @version 2024/6/7
 */
public class DateFormatCache {

    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT_CACHE = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static SimpleDateFormat getSimpleDateFormat() {
        return SIMPLE_DATE_FORMAT_CACHE.get();
    }

    public static void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        SIMPLE_DATE_FORMAT_CACHE.set(simpleDateFormat);
    }

    public static void removeSimpleDateFormat() {
        SIMPLE_DATE_FORMAT_CACHE.remove();
    }
}
