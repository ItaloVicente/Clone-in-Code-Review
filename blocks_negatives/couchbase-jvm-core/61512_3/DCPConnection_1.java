    /**
     * FIXME: At the moment, we cannot use send() to schedule BufferAcknowledgmentRequest,
     *        because requests and responses will interleave in DCPHandler. Instead the handler
     *        store context object here, and DCPConnection can send acknowledgment as soon as
     *        consumer signals about processed events.
     */
    /*package*/ void setLastContext(ChannelHandlerContext ctx) {
        lastCtx = ctx;
    }

    private BinaryMemcacheRequest createBufferAcknowledgmentRequest(int bufferBytes) {
        ByteBuf extras = lastCtx.alloc().buffer(4).writeInt(bufferBytes);
