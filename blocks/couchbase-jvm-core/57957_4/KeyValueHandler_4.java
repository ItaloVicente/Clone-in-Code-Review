    private static CouchbaseResponse handleSubdocumentResponseMessages(BinaryRequest request, FullBinaryMemcacheResponse msg,
         ChannelHandlerContext ctx, ResponseStatus status, boolean seqOnMutation) {
        if (!(request instanceof BinarySubdocRequest))
            return null;
        BinarySubdocRequest subdocRequest = (BinarySubdocRequest) request;
        long cas = msg.getCAS();
        short statusCode = msg.getStatus();
        String bucket = request.bucket();

        MutationToken mutationToken = null;
        if (msg.getExtrasLength() > 0) {
            mutationToken = extractToken(seqOnMutation, status.isSuccess(), msg.getExtras(), request.partition());
        }

        ByteBuf fragment;
        if (msg.content() != null && msg.content().readableBytes() > 0) {
            fragment = msg.content();
        } else if (msg.content() != null) {
            msg.content().release();
            fragment = Unpooled.EMPTY_BUFFER;
        } else {
            fragment = Unpooled.EMPTY_BUFFER;
        }

        return new SimpleSubdocResponse(status, statusCode, bucket, fragment, subdocRequest, cas, mutationToken);
    }

