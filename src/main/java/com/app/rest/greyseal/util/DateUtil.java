package com.app.rest.greyseal.util;

import java.util.Date;

public final class DateUtil {

    private DateUtil() {
    }

    public static Date getDate(final Date date) {
        return date != null ? new Date(date.getTime()) : new Date();
    }
}