
package com.couchbase.client.core.message.kv.subdoc.multi;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.AbstractKeyValueRequest;
import com.couchbase.client.core.message.kv.subdoc.BinarySubdocMultiLookupRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.Arrays;
import java.util.List;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class SubMultiLookupRequest extends AbstractKeyValueRequest implements BinarySubdocMultiLookupRequest {

    private final List<LookupCommand> commands;
    private final ByteBuf encoded;

    public SubMultiLookupRequest(String key, String bucket, LookupCommand... commands) {
        super(key, bucket, null);
        if (commands == null) {
            throw new NullPointerException("At least one lookup command is necessary");
        }
        this.commands = Arrays.asList(commands);
        this.encoded = encode(this.commands);
    }

    private static ByteBuf encode(List<LookupCommand> commands) {
        CompositeByteBuf compositeBuf = Unpooled.compositeBuffer(commands.size()); //FIXME pooled allocator?
        for (LookupCommand command : commands) {
            byte[] pathBytes = command.path().getBytes(CharsetUtil.UTF_8);
            short pathLength = (short) pathBytes.length;

            ByteBuf commandBuf = Unpooled.buffer(4 + pathLength); //FIXME a way of using the pooled allocator?
            commandBuf.writeByte(command.opCode());
            commandBuf.writeByte(0); //no flags supported for lookup
            commandBuf.writeShort(pathLength);
            commandBuf.writeBytes(pathBytes);

            compositeBuf.addComponent(commandBuf);
            compositeBuf.writerIndex(compositeBuf.writerIndex() + commandBuf.readableBytes());
        }
        return compositeBuf;
    }

    @Override
    public List<LookupCommand> commands() {
        return this.commands;
    }

    @Override
    public ByteBuf content() {
        return this.encoded;
    }
}
