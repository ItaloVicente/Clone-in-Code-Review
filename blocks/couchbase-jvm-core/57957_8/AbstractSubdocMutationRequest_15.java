
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationCommand {

    private final Mutation mutation;
    private final String path;
    private final ByteBuf fragment;

    public MutationCommand(Mutation mutation, String path, ByteBuf fragment) {
        this.mutation = mutation;
        this.path = path;
        this.fragment = fragment;
    }
}
