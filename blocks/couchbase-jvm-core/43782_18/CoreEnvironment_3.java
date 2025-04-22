
package com.couchbase.client.core.endpoint.dcp;

import com.couchbase.client.core.message.dcp.DCPRequest;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class DCPStream {
    public final int id;
    public final String bucket;
    public final PublishSubject<DCPRequest> subject;

    public DCPStream(int id, String bucket) {
        this.id = id;
        this.bucket = bucket;
        subject = PublishSubject.create();
    }

    public PublishSubject<DCPRequest> subject() {
        return subject;
    }

    public String bucket() {
        return bucket;
    }
}
