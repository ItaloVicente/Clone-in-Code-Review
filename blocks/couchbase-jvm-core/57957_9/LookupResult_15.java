
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LookupCommand {

    private final Lookup lookup;
    private final String path;

    public LookupCommand(Lookup lookup, String path) {
        this.lookup = lookup;
        this.path = path;
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
}
