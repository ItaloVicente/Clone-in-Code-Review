    private RawQueryResponse handleRawQueryResponse(boolean lastChunk, ChannelHandlerContext ctx) {
        if (!lastChunk) {
            return null;
        }
        ResponseStatus status = ResponseStatusConverter.fromHttp(responseHeader.getStatus().code());
        ByteBuf responseCopy = ctx.alloc().buffer(responseContent.readableBytes(), responseContent.readableBytes());
        responseCopy.writeBytes(responseContent);

        cleanupQueryStates();

        return new RawQueryResponse(status, currentRequest(), responseCopy,
                responseHeader.getStatus().code(),
                responseHeader.getStatus().reasonPhrase());
    }

