package com.couchbase.client.java.search.queries;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class GeoDistanceQuery extends AbstractFtsQuery {

    private final float locationLon;
    private final float locationLat;
    private final String distance;

    private String field;

    public GeoDistanceQuery(float locationLon, float locationLat, String distance) {
        this.locationLon = locationLon;
        this.locationLat = locationLat;
        this.distance = distance;
    }

    public GeoDistanceQuery field(String field) {
        this.field = field;
        return this;
    }

    @Override
    public GeoDistanceQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        input.put("location", JsonArray.from(locationLon, locationLat));
        input.put("distance", distance);
        if (field != null) {
            input.put("field", field);
        }
    }
}
