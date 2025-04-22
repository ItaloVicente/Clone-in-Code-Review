        remoteSocketName = remoteHostname + ":" + remotePort;

        SocketAddress localAddr = ctx.channel().localAddress();
        String localHostname;
        int localPort;
        if (localAddr instanceof InetSocketAddress) {
            InetSocketAddress ia = ((InetSocketAddress) localAddr);
            localHostname = ia.getAddress().getHostAddress();
            localPort = ia.getPort();
        } else {
            localHostname = localAddr.toString();
            localPort = 0;
        }
        localSocketName = localHostname + ":" + localPort;

