package com.f3rog.alf.network;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Class {@link com.f3rog.alf.network.JsonParser} is used for parsing {@link Object}s to and from {@link org.json.JSONObject}.
 *
 * @author f3rog
 * @version 2014-07-16
 */
public class JsonParser {

    private static Gson g_singleton;

    private static Gson getGson() {
        if (g_singleton == null) {
            g_singleton = new Gson();
        }
        return g_singleton;
    }

    /**
     * Parses given {@link org.json.JSONObject} to object of given class.
     *
     * @param json Json
     * @param cls  Class
     * @return Parsed object
     */
    public static <T> T fromJson(JSONObject json, Class<T> cls) {
        if (json == null) {
            throw new IllegalArgumentException("json cannot be null");
        }
        if (cls == null) {
            throw new IllegalArgumentException("cls cannot be null");
        }
        return getGson().fromJson(json.toString(), cls);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        if (json == null) {
            throw new IllegalArgumentException("json cannot be null");
        }
        if (cls == null) {
            throw new IllegalArgumentException("cls cannot be null");
        }
        return getGson().fromJson(json.toString(), cls);
    }

    /**
     * Converts given object to {@link org.json.JSONObject}.
     *
     * @param object Object
     * @return Created Json
     */
    public static JSONObject toJson(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object cannot be null");
        }
        try {
            String json = getGson().toJson(object);
            return new JSONObject(json);
        } catch (Exception e) {
            throw new IllegalStateException("Json not created");
        }
    }
}
