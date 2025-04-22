package com.couchbase.client.java.view;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.Query;

import java.net.URLEncoder;

public class SpatialViewQuery {

    private static final int PARAM_LIMIT_OFFSET = 0;
    private static final int PARAM_SKIP_OFFSET = 2;
    private static final int PARAM_STALE_OFFSET = 4;
    private static final int PARAM_DEBUG_OFFSET = 6;
    private static final int PARAM_START_RANGE_OFFSET = 8;
    private static final int PARAM_END_RANGE_OFFSET = 10;
    private static final int PARAM_ONERROR_OFFSET = 12;

    private static final int NUM_PARAMS = 7;

    private final String[] params;

    private final String design;
    private final String view;

    private boolean development;

    private SpatialViewQuery(String design, String view) {
        this.design = design;
        this.view = view;
        params = new String[NUM_PARAMS * 2];
    }

    public static SpatialViewQuery from(String design, String view) {
        return new SpatialViewQuery(design, view);
    }

    public SpatialViewQuery development() {
        return development(true);
    }

    public SpatialViewQuery development(boolean development) {
        this.development = development;
        return this;
    }

    public SpatialViewQuery limit(final int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must be >= 0.");
        }
        params[PARAM_LIMIT_OFFSET] = "limit";
        params[PARAM_LIMIT_OFFSET+1] = Integer.toString(limit);
        return this;
    }

    public SpatialViewQuery skip(final int skip) {
        if (skip < 0) {
            throw new IllegalArgumentException("Skip must be >= 0.");
        }
        params[PARAM_SKIP_OFFSET] = "skip";
        params[PARAM_SKIP_OFFSET+1] = Integer.toString(skip);
        return this;
    }

    public SpatialViewQuery stale(final Stale stale) {
        params[PARAM_STALE_OFFSET] = "stale";
        params[PARAM_STALE_OFFSET+1] = stale.identifier();
        return this;
    }

    public SpatialViewQuery debug() {
        return debug(true);
    }

    public SpatialViewQuery debug(boolean debug) {
        params[PARAM_DEBUG_OFFSET] = "debug";
        params[PARAM_DEBUG_OFFSET+1] = Boolean.toString(debug);
        return this;
    }

    public SpatialViewQuery startRange(final JsonArray startRange) {
        params[PARAM_START_RANGE_OFFSET] = "start_range";
        params[PARAM_START_RANGE_OFFSET+1] = startRange.toString();
        return this;
    }

    public SpatialViewQuery endRange(final JsonArray endRange) {
        params[PARAM_END_RANGE_OFFSET] = "end_range";
        params[PARAM_END_RANGE_OFFSET+1] = endRange.toString();
        return this;
    }

    public SpatialViewQuery range(final JsonArray startRange, final JsonArray endRange) {
        startRange(startRange);
        endRange(endRange);
        return this;
    }

    public SpatialViewQuery onError(final OnError onError) {
        params[PARAM_ONERROR_OFFSET] = "on_error";
        params[PARAM_ONERROR_OFFSET+1] = onError.identifier();
        return this;
    }

    protected String encode(final String source) {
        try {
            return URLEncoder.encode(source, "UTF-8");
        } catch(Exception ex) {
            throw new RuntimeException("Could not prepare view argument: " + ex);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean firstParam = true;
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                i++;
                continue;
            }

            boolean even = i % 2 == 0;
            if (even) {
                if (!firstParam) {
                    sb.append("&");
                }
            }
            sb.append(params[i]);
            firstParam = false;
            if (even) {
                sb.append('=');
            }
        }
        return sb.toString();
    }

    public String getDesign() {
        return design;
    }

    public String getView() {
        return view;
    }

    public boolean isDevelopment() {
        return development;
    }

}
