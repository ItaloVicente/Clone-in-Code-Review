package com.couchbase.client.java.document;

import com.couchbase.client.core.message.kv.MutationToken;

public interface MutatedDocument {

    String id();

    MutationToken mutationToken();

}
