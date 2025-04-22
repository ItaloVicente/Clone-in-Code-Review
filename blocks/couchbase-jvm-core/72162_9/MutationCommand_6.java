
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiLookupRequest;
@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupCommandBuilder {

    private final Lookup lookup;
    private final String path;
    private boolean attributeAccess;

    public LookupCommandBuilder(Lookup lookup, String path) {
        this.lookup = lookup;
        this.path = path;
    }

    public LookupCommand build(){
        return new LookupCommand(this);
    }

    public Lookup lookup() {
        return lookup;
    }

    public String path() {
        return path;
    }

    public byte opCode() {
        return lookup.opCode();
    }

    public boolean attributeAccess() { return this.attributeAccess; }

    public LookupCommandBuilder attributeAccess(boolean attributeAccess) {
        this.attributeAccess = attributeAccess;
        return this;
    }

}
