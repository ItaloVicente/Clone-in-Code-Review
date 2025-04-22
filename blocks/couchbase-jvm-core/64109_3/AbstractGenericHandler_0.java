
        SocketAddress addr = ctx.channel().remoteAddress();
        if (addr instanceof InetSocketAddress) {
            remoteHostname = ((InetSocketAddress) addr).getAddress().getHostAddress();
        } else {
            remoteHostname = addr.toString();
        }
