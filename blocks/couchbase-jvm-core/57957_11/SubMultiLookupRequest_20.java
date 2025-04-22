
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiMutationRequest;
import io.netty.buffer.ByteBuf;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationCommand {

    private final Mutation mutation;
    private final String path;
    private final ByteBuf fragment;
    private final boolean createIntermediaryPath;

    public MutationCommand(Mutation mutation, String path, ByteBuf fragment, boolean createIntermediaryPath) {
        this.mutation = mutation;
        this.path = path;
        this.fragment = fragment;
        this.createIntermediaryPath = createIntermediaryPath;
    }

    public MutationCommand(Mutation mutation, String path, ByteBuf fragment) {
        this(mutation, path, fragment, false);
    }

    public Mutation mutation() {
        return mutation;
    }

    public String path() {
        return path;
    }

        return fragment;
    }

    public byte opCode() {
        return mutation.opCode();
    }

    public boolean createIntermediaryPath() {
        return createIntermediaryPath;
    }
}
