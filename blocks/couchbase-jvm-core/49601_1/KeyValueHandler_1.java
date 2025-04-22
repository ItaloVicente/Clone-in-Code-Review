        finishedDecoding();
        return response;
    }

    private static CouchbaseResponse handleCommonResponseMessages(BinaryRequest request, FullBinaryMemcacheResponse msg,
         ChannelHandlerContext ctx, ResponseStatus status) {
        CouchbaseResponse response = null;
        ByteBuf content = msg.content();
