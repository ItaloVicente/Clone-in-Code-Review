    protected String remoteHttpHost(ChannelHandlerContext ctx) {
        if (remoteHttpHost == null) {
            remoteHttpHost = ctx.channel().remoteAddress().toString();
        }
        return remoteHttpHost;
    }

