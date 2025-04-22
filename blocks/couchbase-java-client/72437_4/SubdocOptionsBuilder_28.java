
package com.couchbase.client.java.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Uncommitted
@InterfaceAudience.Public
public class SubdocOptionsBuilder {
    private boolean createParents;
    private boolean attributeAccess;

    public SubdocOptionsBuilder() {
    }

    public static SubdocOptionsBuilder builder() {
        return new SubdocOptionsBuilder();
    }

    public SubdocOptionsBuilder createParents(boolean createParents) {
        this.createParents = createParents;
        return this;
    }

    public boolean createParents() {
        return this.createParents;
    }

    @InterfaceStability.Experimental
    public SubdocOptionsBuilder attributeAccess(boolean attributeAccess) {
        this.attributeAccess = attributeAccess;
        return this;
    }

    public boolean attributeAccess() {
        return this.attributeAccess;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (createParents) {
            sb.append(" createParents ");
        }
        if (attributeAccess && createParents) {
            sb.append(", attributeAccess ");
        } else if (attributeAccess) {
            sb.append(" attributeAccess ");
        }
        sb.append("}");
        return sb.toString();
    }
}
