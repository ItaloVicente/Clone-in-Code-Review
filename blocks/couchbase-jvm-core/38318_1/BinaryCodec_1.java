    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof SupportedDatatypes) {
            datatypes = (SupportedDatatypes) evt;
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

