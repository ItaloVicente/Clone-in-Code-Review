package com.couchbase.client.java.datastructures;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.RequestCancelledException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.*;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class CouchbaseMap<V> {

	private final String docId;
	private final Bucket bucket;
	private final Class<V> type;

	public CouchbaseMap(Bucket bucket, String docId, Class<V> type) {
		this.bucket = bucket;
		this.docId = docId;
		this.type = type;
		if (!bucket.exists(docId)) {
			bucket.insert(JsonDocument.create(docId, JsonObject.create()));
		}
	}

	public Boolean add(final String key, final V value) {
		return this.bucket.mapAdd(this.docId, key, value);
	}

	public Boolean add(final String key, final V value, final MutationOptionBuilder mutationOptionBuilder) {
		return this.bucket.mapAdd(this.docId, key, value, mutationOptionBuilder);
	}

	public V get(final String key) {
		return this.bucket.mapGet(this.docId, key, type);
	}

	public Boolean remove(final String key) {
		return this.bucket.mapRemove(this.docId, key);
	}

	public Boolean remove(final String key, final MutationOptionBuilder mutationOptionBuilder) {
		return this.bucket.mapRemove(this.docId, key, mutationOptionBuilder);
	}

	public int size() {
		return this.bucket.mapSize(this.docId);
	}
}
