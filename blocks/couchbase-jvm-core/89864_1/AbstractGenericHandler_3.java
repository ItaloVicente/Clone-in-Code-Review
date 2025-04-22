            remoteSocket = addr.toString();
        }

        SocketAddress localAddr = ctx.channel().localAddress();
        if (localAddr instanceof InetSocketAddress) {
            InetSocketAddress ia = ((InetSocketAddress) localAddr);
            localSocket = ia.getAddress().getHostAddress() + ":" + ia.getPort();
        } else {
            localSocket = localAddr.toString();
