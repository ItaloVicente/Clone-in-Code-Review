package com.couchbase.client.java.cluster.api;

import java.util.LinkedHashMap;
import java.util.Map;

import com.couchbase.client.deps.io.netty.handler.codec.http.QueryStringEncoder;

public class Form {

    private final Map<String, String> formValues;

    private Form() {
        this.formValues = new LinkedHashMap<String, String>();
    }

    public static Form create() {
        return new Form();
    }

    public Form add(String paramName, String paramValue) {
        this.formValues.put(paramName, paramValue);
        return this;
    }

    public String toUrlEncodedString() {
        QueryStringEncoder encoder = new QueryStringEncoder("");
        for (Map.Entry<String, String> entry : formValues.entrySet()) {
            encoder.addParam(entry.getKey(), entry.getValue());
        }
        StringBuilder ues = new StringBuilder(encoder.toString());
        if (ues.length() > 0 && ues.charAt(0) == '?') {
            ues.deleteCharAt(0);
        }
        return ues.toString();
    }

}
