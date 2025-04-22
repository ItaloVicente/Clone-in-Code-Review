
        SocketAddress addr = ctx.channel().remoteAddress();
        if (addr instanceof InetSocketAddress) {
            remoteHostname = ((InetSocketAddress) addr).getAddress().getHostAddress();
        } else {
            remoteHostname = ctx.channel().remoteAddress().toString();
        }
