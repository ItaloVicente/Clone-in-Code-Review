package com.couchbase.client.java;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.CouchbaseRequest;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public interface CoreHookAware {

    void beforeSend(CouchbaseRequest request);

}
