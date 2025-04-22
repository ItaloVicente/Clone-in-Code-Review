package com.couchbase.client.core.utils.yasjl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.couchbase.client.core.utils.yasjl.Callbacks.JsonPointerCB;

public class JsonPointer {

    public static final int MAX_NESTING_LEVEL = 31;
    private static final String ROOT_TOKEN = "";

    private List<String> refTokens;
    private JsonPointerCB jsonPointerCB;

    protected JsonPointer() {
        this.refTokens = new ArrayList<String>();
        this.addToken(ROOT_TOKEN);
    }

    public JsonPointer(final List<String> refTokens) {
        this.refTokens = new ArrayList<String>(refTokens);
    }

    public JsonPointer(final String path) {
        this(path, null);
    }

    public JsonPointer(final String path, final JsonPointerCB jsonPointerCB) {
        parseComponents(path);
        this.jsonPointerCB = jsonPointerCB;
    }

    private void parseComponents(final String path) {
        this.refTokens = new ArrayList<String>();

        String[] tokens = path.split("/");

        if (tokens.length > MAX_NESTING_LEVEL) {
            throw new IllegalArgumentException("path contains too many levels of nesting");
        }

        for (int i=0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replace("~1","/").replace("~0","~");
        }

        this.refTokens.addAll(Arrays.asList(tokens));
    }

    protected void addToken(String token) {
        this.refTokens.add(token);
    }

    protected void removeToken() {
        if (this.refTokens.size() > 1) {
            this.refTokens.remove(this.refTokens.size() - 1);
        }
    }

    protected List<String> refTokens() {
        return this.refTokens;
    }

    protected JsonPointerCB jsonPointerCB() {
        return this.jsonPointerCB;
    }

    protected void jsonPointerCB(final JsonPointerCB jsonPointerCB) {
        this.jsonPointerCB = jsonPointerCB;
    }

    private String path() {
        StringBuilder sb = new StringBuilder();
        for(String refToken:this.refTokens) {
            sb.append("/");
            sb.append(refToken);
        }
        return sb.substring(1);
    }

    @Override
    public String toString() {
        return "JsonPointer{path=" + path() + "}";
    }

}
