package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;

public class BinaryMemcacheResponseEncoder
    extends AbstractBinaryMemcacheEncoder<BinaryMemcacheResponse> {

    @Override
    protected void encodeHeader(ByteBuf buf, BinaryMemcacheResponse msg) {
        buf.writeByte(msg.getMagic());
        buf.writeByte(msg.getOpcode());
        buf.writeShort(msg.getKeyLength());
        buf.writeByte(msg.getExtrasLength());
        buf.writeByte(msg.getDataType());
        buf.writeShort(msg.getStatus());
        buf.writeInt(msg.getTotalBodyLength());
        buf.writeInt(msg.getOpaque());
        buf.writeLong(msg.getCAS());
    }

}
