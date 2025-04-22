
package com.couchbase.client.core.message.search;

import com.couchbase.client.core.message.CouchbaseRequest;

public interface SearchRequest extends CouchbaseRequest {
    String path();
}
