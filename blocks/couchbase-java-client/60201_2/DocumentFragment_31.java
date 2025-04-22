
package com.couchbase.client.java.subdoc;

import java.util.Collections;
import java.util.List;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DocumentFragment<OPERATION> {

    private final String id;
    private final long cas;
    private final MutationToken mutationToken;

    private final List<MultiResult<OPERATION>> resultList;

    public DocumentFragment(String id, long cas, MutationToken mutationToken, List<MultiResult<OPERATION>> resultList) {
        this.id = id;
        this.cas = cas;
        this.mutationToken = mutationToken;

        this.resultList = resultList;
    }

    public String id() {
        return this.id;
    }

    public long cas() {
        return this.cas;
    }

    public MutationToken mutationToken() {
        return this.mutationToken;
    }

    public int size() {
        return resultList.size();
    }

    public <T> T content(String path, Class<T> targetClass) {
        if (path == null) {
            return null;
        }
        for (MultiResult<OPERATION> result : resultList) {
            if (path.equals(result.path())) {
                return interpretResult(result);
            }
        }
        return null;
    }

    public Object content(String path) {
        return this.content(path, Object.class);
    }

    public <T> T content(int index, Class<T> targetClass) {
        return interpretResult(resultList.get(index));
    }

    private <T> T interpretResult(MultiResult<OPERATION> result) {
        if (result.status() == ResponseStatus.FAILURE && result.value() instanceof RuntimeException) {
            throw (RuntimeException) result.value();
        } else if (result.value() instanceof CouchbaseException) {
            throw (CouchbaseException) result.value();
        } else {
            return (T) result.value();
        }
    }

    public Object content(int index) {
        return this.content(index, Object.class);
    }

    public ResponseStatus status(String path) {
        if (path == null) {
            return null;
        }
        for (MultiResult<OPERATION> result : resultList) {
            if (path.equals(result.path())) {
                return result.status();
            }
        }
        return null;
    }

    public ResponseStatus status(int index) {
        return resultList.get(index).status();
    }

    public boolean exists(String path) {
        if (path == null) {
            return false;
        }
        for (MultiResult<OPERATION> result : resultList) {
            if (path.equals(result.path())) {
                return result.exists();
            }
        }
        return false;
    }

    public boolean exists(int specIndex) {
        return specIndex >= 0 && specIndex < resultList.size()
                && resultList.get(specIndex).exists();
    }

    @Override
    public String toString() {
        return "DocumentFragment{" +
                "id='" + id + '\'' +
                ", cas=" + cas +
                ", mutationToken=" + mutationToken +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentFragment<?> that = (DocumentFragment<?>) o;

        if (cas != that.cas) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (mutationToken != null ? !mutationToken.equals(that.mutationToken) : that.mutationToken != null) {
            return false;
        }
        return resultList.equals(that.resultList);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (int) (cas ^ (cas >>> 32));
        result = 31 * result + (mutationToken != null ? mutationToken.hashCode() : 0);
        result = 31 * result + resultList.hashCode();
        return result;
    }
}
