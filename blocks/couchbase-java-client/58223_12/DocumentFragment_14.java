
package com.couchbase.client.java.document.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.MutationToken;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DocumentFragment<T> implements IDocumentFragment<T> {

    private String id;
    private String path;
    private T fragment;
    private long cas;
    private int expiry;
    private MutationToken mutationToken;

    private DocumentFragment(String id, String path, int expiry, T fragment, long cas, MutationToken mutationToken) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The DocumentFragment ID must not be null or empty.");
        }
        if (id.getBytes().length > 250) {
            throw new IllegalArgumentException("The DocumentFragment ID must not be larger than 250 bytes");
        }
        if (expiry < 0) {
            throw new IllegalArgumentException("The DocumentFragment expiry must not be negative.");
        }
        this.id = id;
        this.path = path;
        this.fragment = fragment;
        this.cas = cas;
        this.expiry = expiry;
        this.mutationToken = mutationToken;
    }

    @InterfaceAudience.Private
    public static <T> DocumentFragment<T> create(String id, String path, int expiry, T fragment, long cas,
            MutationToken mutationToken) {
        return new DocumentFragment<T>(id, path, expiry, fragment, cas, mutationToken);
    }

    public static <T> DocumentFragment<T> create(String id, String path, int expiry, T fragment) {
        return new DocumentFragment<T>(id, path, expiry, fragment, 0L, null);
    }

    public static <T> DocumentFragment<T> create(String id, String path, T fragment, long cas) {
        return new DocumentFragment<T>(id, path, 0, fragment, cas, null);
    }

    public static <T> DocumentFragment<T> create(String id, String path, int expiry, T fragment, long cas) {
        return new DocumentFragment<T>(id, path, expiry, fragment, cas, null);
    }

    public static <T> DocumentFragment<T> create(String id, String path, T fragment) {
        return new DocumentFragment<T>(id, path, 0, fragment, 0L, null);
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String path() {
         return this.path;
    }

    @Override
    public T fragment() {
       return this.fragment;
    }

    @Override
    public long cas() {
        return this.cas;
    }

    @Override
    public int expiry() {
        return this.expiry;
    }

    @Override
    public MutationToken mutationToken() {
        return this.mutationToken;
    }

    @Override
    public String toString() {
        return "DocumentFragment{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", fragment=" + fragment +
                ", cas=" + cas +
                ", expiry=" + expiry +
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
        if (expiry != that.expiry) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!path.equals(that.path)) {
            return false;
        }
        if (fragment != null ? !fragment.equals(that.fragment) : that.fragment != null) {
            return false;
        }
        return mutationToken != null ? mutationToken.equals(that.mutationToken) : that.mutationToken == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + (fragment != null ? fragment.hashCode() : 0);
        result = 31 * result + (int) (cas ^ (cas >>> 32));
        result = 31 * result + expiry;
        result = 31 * result + (mutationToken != null ? mutationToken.hashCode() : 0);
        return result;
    }
}
