
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.CouchbaseResponse;

public interface ZombieResponseReporter {

    void report(CouchbaseResponse request);

    void shutdown();

}
