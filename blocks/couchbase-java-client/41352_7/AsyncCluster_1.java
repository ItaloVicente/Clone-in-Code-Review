package com.couchbase.client.java;

import com.couchbase.client.java.bucket.AsyncBucketManager;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.LongDocument;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.DurabilityException;
import com.couchbase.client.java.query.AsyncQueryResult;
import com.couchbase.client.java.query.Query;
import com.couchbase.client.java.transcoder.Transcoder;
import com.couchbase.client.java.view.AsyncViewResult;
import com.couchbase.client.java.view.ViewQuery;
import rx.Observable;

public interface AsyncBucket {

    String name();

    Observable<JsonDocument> get(String id);

    <D extends Document<?>> Observable<D> get(D document);

    <D extends Document<?>> Observable<D> get(String id, Class<D> target);

    Observable<JsonDocument> getFromReplica(String id, ReplicaMode type);

    <D extends Document<?>> Observable<D> getFromReplica(D document, ReplicaMode type);

    <D extends Document<?>> Observable<D> getFromReplica(String id, ReplicaMode type, Class<D> target);

    Observable<JsonDocument> getAndLock(String id, int lockTime);

    <D extends Document<?>> Observable<D> getAndLock(D document, int lockTime);

    <D extends Document<?>> Observable<D> getAndLock(String id, int lockTime, Class<D> target);

    Observable<JsonDocument> getAndTouch(String id, int expiry);

    <D extends Document<?>> Observable<D> getAndTouch(D document);

    <D extends Document<?>> Observable<D> getAndTouch(String id, int expiry, Class<D> target);

    <D extends Document<?>> Observable<D> insert(D document);

    <D extends Document<?>> Observable<D> insert(D document, PersistTo persistTo, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> insert(D document, PersistTo persistTo);

    <D extends Document<?>> Observable<D> insert(D document, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> upsert(D document);

    <D extends Document<?>> Observable<D> upsert(D document, PersistTo persistTo, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> upsert(D document, PersistTo persistTo);

    <D extends Document<?>> Observable<D> upsert(D document, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> replace(D document);

    <D extends Document<?>> Observable<D> replace(D document, PersistTo persistTo, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> replace(D document, PersistTo persistTo);

    <D extends Document<?>> Observable<D> replace(D document, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> remove(D document);
    <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo, ReplicateTo replicateTo);
    <D extends Document<?>> Observable<D> remove(D document, PersistTo persistTo);
    <D extends Document<?>> Observable<D> remove(D document, ReplicateTo replicateTo);

    Observable<JsonDocument> remove(String id);
    Observable<JsonDocument> remove(String id, PersistTo persistTo, ReplicateTo replicateTo);
    Observable<JsonDocument> remove(String id, PersistTo persistTo);
    Observable<JsonDocument> remove(String id, ReplicateTo replicateTo);

    <D extends Document<?>> Observable<D> remove(String id, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, ReplicateTo replicateTo, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, PersistTo persistTo, Class<D> target);
    <D extends Document<?>> Observable<D> remove(String id, ReplicateTo replicateTo, Class<D> target);

    Observable<AsyncViewResult> query(ViewQuery query);
    Observable<AsyncQueryResult> query(Query query);
    Observable<AsyncQueryResult> query(String query);

    Observable<Boolean> unlock(String id, long cas);
    <D extends Document<?>> Observable<Boolean> unlock(D document);

    Observable<Boolean> touch(String id, int expiry);
    <D extends Document<?>> Observable<Boolean> touch(D document);

    Observable<LongDocument> counter(String id, long delta);
    Observable<LongDocument> counter(String id, long delta, long initial);
    Observable<LongDocument> counter(String id, long delta, long initial, int expiry);

    Observable<AsyncBucketManager> bucketManager();

    <D extends Document<?>> Observable<D> append(D document);
    <D extends Document<?>> Observable<D> prepend(D document);

    Observable<Boolean> close();

}
