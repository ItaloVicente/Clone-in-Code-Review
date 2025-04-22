
package com.couchbase.client.java.subdoc;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.deps.io.netty.util.internal.StringUtil;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.TranscodingException;
import com.couchbase.client.java.error.subdoc.DocumentNotJsonException;
import com.couchbase.client.java.error.subdoc.SubDocumentException;
import com.couchbase.client.java.util.Blocking;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupInBuilder {

    private final AsyncLookupInBuilder async;
    private final long defaultTimeout;
    private final TimeUnit defaultTimeUnit;

    @InterfaceAudience.Private
    public LookupInBuilder(AsyncLookupInBuilder async, long defaultTimeout, TimeUnit defaultTimeUnit) {
        this.async = async;
        this.defaultTimeout = defaultTimeout;
        this.defaultTimeUnit = defaultTimeUnit;
    }

    public DocumentFragment<Lookup> doLookup() {
        return doLookup(defaultTimeout, defaultTimeUnit);
    }

    public DocumentFragment<Lookup> doLookup(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(this.async.doLookup(), timeout, timeUnit);
    }

    public LookupInBuilder get(String path) {
        this.async.get(path);
        return this;
    }

    public LookupInBuilder exists(String path) {
        this.async.exists(path);
        return this;
    }

    @Override
    public String toString() {
        return async.toString();
    }
}
