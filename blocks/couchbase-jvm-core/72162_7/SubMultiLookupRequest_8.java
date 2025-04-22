
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationCommandBuilder {
    private Mutation mutation;
    private String path;
    private ByteBuf fragment;
    private boolean createIntermediaryPath;
    private boolean attributeAccess;

    public MutationCommandBuilder(Mutation mutation, String path) {
        this.mutation = mutation;
        this.path = path;
        this.fragment = Unpooled.EMPTY_BUFFER;
    }

    public MutationCommandBuilder(Mutation mutation, String path, ByteBuf fragment) {
        this.mutation = mutation;
        this.path = path;
        this.fragment = (fragment == null) ? Unpooled.EMPTY_BUFFER : fragment;
    }

    public MutationCommand build() {
        return new MutationCommand(this);
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

    public boolean attributeAccess() { return attributeAccess; }

    public MutationCommandBuilder createIntermediaryPath(boolean createIntermediaryPath) {
        this.createIntermediaryPath = createIntermediaryPath;
        return this;
    }

    public MutationCommandBuilder fragment(ByteBuf fragment) {
        this.fragment = fragment;
        return this;
    }

    public MutationCommandBuilder attributeAccess(boolean attributeAccess) {
        this.attributeAccess = attributeAccess;
        return this;
    }
}
