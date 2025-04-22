package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.memcache.AbstractMemcacheObjectAggregator;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.MemcacheObject;
import io.netty.util.ReferenceCountUtil;

import java.util.List;

public class BinaryMemcacheObjectAggregator extends AbstractMemcacheObjectAggregator {

    private boolean tooLongFrameFound;

    public BinaryMemcacheObjectAggregator(int maxContentLength) {
        super(maxContentLength);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, MemcacheObject msg, List<Object> out) throws Exception {
        FullMemcacheMessage currentMessage = this.currentMessage;

        if (msg instanceof MemcacheMessage) {
            tooLongFrameFound = false;
            MemcacheMessage m = (MemcacheMessage) msg;

            if (!m.getDecoderResult().isSuccess()) {
                out.add(toFullMessage(m));
                this.currentMessage = null;
                return;
            }

            if (msg instanceof BinaryMemcacheRequest) {
                this.currentMessage = toFullRequest((BinaryMemcacheRequest) msg,
                    Unpooled.compositeBuffer(getMaxCumulationBufferComponents()));
            } else if (msg instanceof BinaryMemcacheResponse) {
                this.currentMessage = toFullResponse((BinaryMemcacheResponse) msg,
                    Unpooled.compositeBuffer(getMaxCumulationBufferComponents()));
            } else {
                throw new Error();
            }
        } else if (msg instanceof MemcacheContent) {
            if (tooLongFrameFound) {
                if (msg instanceof LastMemcacheContent) {
                    this.currentMessage = null;
                }
                return;
            }

            MemcacheContent chunk = (MemcacheContent) msg;
            CompositeByteBuf content = (CompositeByteBuf) currentMessage.content();

            if (content.readableBytes() > getMaxContentLength() - chunk.content().readableBytes()) {
                tooLongFrameFound = true;

                currentMessage.release();
                this.currentMessage = null;

                throw new TooLongFrameException("Memcache content length exceeded " + getMaxContentLength()
                    + " bytes.");
            }

            if (chunk.content().isReadable()) {
                chunk.retain();
                content.addComponent(chunk.content());
                content.writerIndex(content.writerIndex() + chunk.content().readableBytes());
            }

            final boolean last;
            if (!chunk.getDecoderResult().isSuccess()) {
                currentMessage.setDecoderResult(
                    DecoderResult.failure(chunk.getDecoderResult().cause()));
                last = true;
            } else {
                last = chunk instanceof LastMemcacheContent;
            }

            if (last) {
                this.currentMessage = null;
                out.add(currentMessage);
            }
        } else {
            throw new Error();
        }
    }

    private static FullMemcacheMessage toFullMessage(final MemcacheMessage msg) {
        if (msg instanceof FullMemcacheMessage) {
            return ((FullMemcacheMessage) msg).retain();
        }

        FullMemcacheMessage fullMsg;
        if (msg instanceof BinaryMemcacheRequest) {
            fullMsg = toFullRequest((BinaryMemcacheRequest) msg, Unpooled.EMPTY_BUFFER);
        } else if (msg instanceof BinaryMemcacheResponse) {
            fullMsg = toFullResponse((BinaryMemcacheResponse) msg, Unpooled.EMPTY_BUFFER);
        } else {
            throw new IllegalStateException();
        }

        return fullMsg;
    }

    private static FullBinaryMemcacheRequest toFullRequest(BinaryMemcacheRequest request, ByteBuf content) {
        FullBinaryMemcacheRequest fullRequest = new DefaultFullBinaryMemcacheRequest(request.getKey(),
            request.getExtras(), content);

        fullRequest.setMagic(request.getMagic());
        fullRequest.setOpcode(request.getOpcode());
        fullRequest.setKeyLength(request.getKeyLength());
        fullRequest.setExtrasLength(request.getExtrasLength());
        fullRequest.setDataType(request.getDataType());
        fullRequest.setTotalBodyLength(request.getTotalBodyLength());
        fullRequest.setOpaque(request.getOpaque());
        fullRequest.setCAS(request.getCAS());
        fullRequest.setReserved(request.getReserved());

        return fullRequest;
    }

    private static FullBinaryMemcacheResponse toFullResponse(BinaryMemcacheResponse response, ByteBuf content) {
        FullBinaryMemcacheResponse fullResponse = new DefaultFullBinaryMemcacheResponse(response.getKey(),
            response.getExtras(), content);

        fullResponse.setMagic(response.getMagic());
        fullResponse.setOpcode(response.getOpcode());
        fullResponse.setKeyLength(response.getKeyLength());
        fullResponse.setExtrasLength(response.getExtrasLength());
        fullResponse.setDataType(response.getDataType());
        fullResponse.setTotalBodyLength(response.getTotalBodyLength());
        fullResponse.setOpaque(response.getOpaque());
        fullResponse.setCAS(response.getCAS());
        fullResponse.setStatus(response.getStatus());

        return fullResponse;
    }

}
