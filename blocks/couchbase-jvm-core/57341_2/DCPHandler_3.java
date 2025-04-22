    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        connection.subject().onCompleted();
        super.handlerRemoved(ctx);
    }

