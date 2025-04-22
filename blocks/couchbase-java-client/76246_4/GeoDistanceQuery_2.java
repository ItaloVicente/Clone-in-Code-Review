package com.couchbase.client.java.search.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class GeoBoundingBoxQuery extends AbstractFtsQuery {

    private final double topLeftLat;
    private final double topLeftLon;
    private final double bottomRightLat;
    private final double bottomRightLon;
    private String field;

    public GeoBoundingBoxQuery(double topLeftLon, double topLeftLat, double bottomRightLon, double bottomRightLat) {
        this.topLeftLat = topLeftLat;
        this.topLeftLon = topLeftLon;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLon = bottomRightLon;
    }

    public GeoBoundingBoxQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public GeoBoundingBoxQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("top_left", JsonArray.from(topLeftLon, topLeftLat));
        input.put("bottom_right", JsonArray.from(bottomRightLon, bottomRightLat));
        if (field != null) {
            input.put("field", field);
        }
    }
}
