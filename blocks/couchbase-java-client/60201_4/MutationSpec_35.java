
package com.couchbase.client.java.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class MutationSpec {
    private final Mutation type;
    private final String path;
    private final Object fragment;
    private final boolean createParents;

    public MutationSpec(Mutation type, String path, Object fragment, boolean createParents) {
        this.type = type;
        this.path = path;
        this.fragment = fragment;
        this.createParents = createParents;
    }

    public Mutation type() {
        return type;
    }

    public String path() {
        return path;
    }

    public Object fragment() {
        return fragment;
    }

    public boolean createParents() {
        return createParents;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{").append(type());
        if (createParents) {
            sb.append(", createParents");
        }
        sb.append(':').append(path()).append('}');
        return sb.toString();
    }
}
