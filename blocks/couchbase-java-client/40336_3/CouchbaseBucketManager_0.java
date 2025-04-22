package com.couchbase.client.java.bucket;

import rx.Observable;

public interface BucketManager {

    Observable<Boolean> flush();

}
