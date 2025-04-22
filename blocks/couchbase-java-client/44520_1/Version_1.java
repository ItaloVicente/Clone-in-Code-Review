package com.couchbase.client.java.util.features;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.cluster.ClusterInfo;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class FeaturesHelper {

    public static synchronized boolean checkAvailable(ClusterInfo info, CouchbaseFeature feature) {
        Version minVersion = getMinVersion(info);
        return feature.isAvailableOn(minVersion);
    }

    protected static Version getMinVersion(ClusterInfo info) {
        Version minVersion = Version.VERY_BIG;
        try {
            JsonObject raw = info.raw();
            if (!raw.containsKey("nodes")) {
                return Version.NO_VERSION;
            }
            JsonArray nodes = raw.getArray("nodes");
            for (int i = 0; i < nodes.size(); i++) {
                JsonObject node = nodes.getObject(i);
                if (node.containsKey("version")) {
                    String versionFull = node.getString("version");
                    Version version = Version.parseVersion(versionFull);
                    if (version.compareTo(minVersion) < 0) {
                            minVersion = version;
                    }
                }
            }
            return minVersion;
        } catch (Exception e) {
            return Version.NO_VERSION;
        }
    }
}
