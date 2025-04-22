
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.endpoint.kv.KeyValueAuthHandler;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import com.couchbase.client.core.message.kv.AbstractKeyValueRequest;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiMutationRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.Arrays;
import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubMultiMutationRequest extends AbstractKeyValueRequest implements BinarySubdocMultiMutationRequest {

    private final List<MutationCommand> commands;
    private final ByteBuf encoded;
    private final int expiration;
    private final long cas;

    public SubMultiMutationRequest(String key, String bucket, int expiration, long cas, List<MutationCommand> commands) {
        super(key, bucket, null);
        if (commands == null || commands.isEmpty()) {
            throw new IllegalArgumentException("At least one mutation command is necessary");
        }
        this.commands = commands;
        this.encoded = encode(commands);
        this.expiration = expiration;
        this.cas = cas;
    }

    public SubMultiMutationRequest(String key, String bucket, int expiration, long cas, MutationCommand... commands) {
        this(key, bucket, expiration, cas, commands == null ? null : Arrays.asList(commands));
    }

    public SubMultiMutationRequest(String key, String bucket, List<MutationCommand> commands) {
        this(key, bucket, 0, 0L, commands);
    }

    public SubMultiMutationRequest(String key, String bucket, MutationCommand... commands) {
        this(key, bucket, 0, 0L, commands);
    }

    private static ByteBuf encode(List<MutationCommand> commands) {
        CompositeByteBuf compositeBuf = Unpooled.compositeBuffer(commands.size());
        for (MutationCommand command : commands) {
            byte[] pathBytes = command.path().getBytes(CharsetUtil.UTF_8);
            short pathLength = (short) pathBytes.length;

            ByteBuf commandBuf = Unpooled.buffer(4 + pathLength + command.fragment().readableBytes());
            commandBuf.writeByte(command.opCode());
            if (command.createIntermediaryPath()) {
                commandBuf.writeByte(KeyValueHandler.SUBDOC_BITMASK_MKDIR_P); //0 | SUBDOC_BITMASK_MKDIR_P
            } else {
                commandBuf.writeByte(0);
            }
            commandBuf.writeShort(pathLength);
            commandBuf.writeInt(command.fragment().readableBytes());
            commandBuf.writeBytes(pathBytes);

            commandBuf.writeBytes(command.fragment(), command.fragment().readerIndex(), command.fragment().readableBytes());
            command.fragment().release();

            compositeBuf.addComponent(commandBuf);
            compositeBuf.writerIndex(compositeBuf.writerIndex() + commandBuf.readableBytes());
        }
        return compositeBuf;
    }

    @Override
    public int expiration() {
        return this.expiration;
    }

    @Override
    public long cas() {
        return this.cas;
    }

    @Override
    public List<MutationCommand> commands() {
        return this.commands;
    }

    @Override
    public ByteBuf content() {
        return this.encoded;
    }
}
