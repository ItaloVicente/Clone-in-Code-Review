
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubMultiLookupDocOptionsBuilder {
    private boolean accessDeleted;

    public static SubMultiLookupDocOptionsBuilder builder() {
        return new SubMultiLookupDocOptionsBuilder();
    }

    public SubMultiLookupDocOptionsBuilder accessDeleted(boolean accessDeleted) {
        this.accessDeleted = accessDeleted;
        return this;
    }

    public boolean accessDeleted() {
        return this.accessDeleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(" \"accessDeleted\": " + accessDeleted);
        sb.append("}");
        return sb.toString();
    }
}
