package com.couchbase.client.java.util.features;

import static org.junit.Assert.*;

import com.couchbase.client.java.cluster.DefaultClusterInfo;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

public class FeaturesHelperTest {

    @Test
    public void shouldDetectCorrectMinimumDespiteGarbage() throws Exception {
        JsonObject node1 = JsonObject.create()
                .put("version", "3.0.2");
        JsonObject node2 = JsonObject.create()
                .put("version", "1.4.6-dp_1324");
        DefaultClusterInfo info = new DefaultClusterInfo(
                JsonObject.create().put("nodes", JsonArray.from(node1, node2)));

        assertEquals(new Version(1, 4, 6), FeaturesHelper.getMinVersion(info));
        assertFalse(FeaturesHelper.checkAvailable(info, CouchbaseFeature.SPATIAL_VIEW));
    }

    @Test
    public void shouldReturnFalseIfBadInfo() {
        JsonObject goodNode = JsonObject.create()
                                     .put("version", "3.0.2");
        JsonObject badNode = JsonObject.create()
                                     .put("nope", "1.4.6-dp_1324");
        DefaultClusterInfo info1 = new DefaultClusterInfo(
                JsonObject.create().put("nodess", JsonArray.from(goodNode)));
        DefaultClusterInfo info2 = new DefaultClusterInfo((JsonObject.create()
            .put("nodes", "string")));
        DefaultClusterInfo info3 = new DefaultClusterInfo((JsonObject.create()
            .put("nodes", JsonArray.from("notANode"))));
        DefaultClusterInfo info4 = new DefaultClusterInfo(JsonObject.create()
            .put("nodes", JsonArray.from(badNode)));

        assertEquals(Version.NO_VERSION, FeaturesHelper.getMinVersion(info1));
        assertEquals(Version.NO_VERSION, FeaturesHelper.getMinVersion(info2));
        assertEquals(Version.NO_VERSION, FeaturesHelper.getMinVersion(info3));
        assertEquals(Version.NO_VERSION, FeaturesHelper.getMinVersion(info4));

        assertFalse(FeaturesHelper.checkAvailable(info1, CouchbaseFeature.KV));
        assertFalse(FeaturesHelper.checkAvailable(info2, CouchbaseFeature.KV));
        assertFalse(FeaturesHelper.checkAvailable(info3, CouchbaseFeature.KV));
        assertFalse(FeaturesHelper.checkAvailable(info4, CouchbaseFeature.KV));

    }

    @Test
    public void shouldReturnFalseIfBadVersionFormat() {
        JsonObject node1 = JsonObject.create()
                                     .put("version", "3.0.2");
        JsonObject node2 = JsonObject.create()
                                     .put("version", "1z.4.6-dp_1324");
        DefaultClusterInfo info = new DefaultClusterInfo(
                JsonObject.create().put("nodes", JsonArray.from(node1, node2)));

        assertEquals(Version.NO_VERSION, FeaturesHelper.getMinVersion(info));
        assertFalse(FeaturesHelper.checkAvailable(info, CouchbaseFeature.KV));
    }
}
