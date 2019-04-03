package com.app.rest.greyseal.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private final static GsonBuilder GSON_BUILDER = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private final static Gson GSON = GSON_BUILDER.create();

    public static String toJsonString(final Object src){
        return GSON.toJson(src);
    }
}