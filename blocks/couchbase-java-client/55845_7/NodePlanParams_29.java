
package com.couchbase.client.java.search;

import com.couchbase.client.java.document.json.JsonObject;

public interface IndexSettings {
    String name();
    String type();
    IndexParams params();

    JsonObject json();
}
