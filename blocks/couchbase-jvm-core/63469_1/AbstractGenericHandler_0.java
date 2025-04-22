    protected String remoteHttpHost(ChannelHandlerContext ctx) {
        if (remoteHttpHost == null) {
            InetSocketAddress addr = (InetSocketAddress) ctx.channel().remoteAddress();
            remoteHttpHost = addr.getAddress().getHostAddress() + ":" + addr.getPort();
        }
        return remoteHttpHost;
    }

