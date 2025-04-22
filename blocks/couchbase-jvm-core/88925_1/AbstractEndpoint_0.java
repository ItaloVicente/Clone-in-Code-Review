                        SocketAddress socketAddress = channel.localAddress();
                        if (socketAddress instanceof InetSocketAddress) {
                            NetworkAddress addr = NetworkAddress.create(
                                ((InetSocketAddress) socketAddress).getAddress().getHostAddress()
                            );
                            localSocket = addr.nameOrAddress() + ":" + ((InetSocketAddress) socketAddress).getPort();
                        } else {
                            localSocket = "127.0.0.1:0000";
                        }
