
package com.couchbase.client.core.endpoint.dcp;

import com.couchbase.client.core.message.dcp.DCPRequest;
import rx.subjects.ReplaySubject;

public class DCPStream {
    public final int id;
    public final String bucket;
    public final ReplaySubject<DCPRequest> subject;

    public DCPStream(int id, String bucket) {
        this.id = id;
        this.bucket = bucket;
        subject = ReplaySubject.create();
    }

    public ReplaySubject<DCPRequest> subject() {
        return subject;
    }

    public String bucket() {
        return bucket;
    }
}
