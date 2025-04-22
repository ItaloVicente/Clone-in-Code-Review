            SocketAddress addr = ctx.channel().remoteAddress();
            if (addr instanceof InetSocketAddress) {
                InetSocketAddress inetAddr = (InetSocketAddress) addr;
                remoteHttpHost = inetAddr.getAddress().getHostAddress() + ":" + inetAddr.getPort();
            } else {
                remoteHttpHost = addr.toString();
            }
