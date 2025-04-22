
package com.couchbase.client.core;

import com.couchbase.client.core.message.CouchbaseRequest;
import rx.functions.Func0;

public interface RequestFactory extends Func0<CouchbaseRequest> {
}
