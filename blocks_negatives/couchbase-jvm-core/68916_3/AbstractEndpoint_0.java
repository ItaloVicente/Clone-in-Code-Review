                            future.channel().eventLoop().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    if (!disconnected) {
                                        doConnect(observable, bootstrapping);
                                    } else {
                                        LOGGER.debug("{}Explicitly breaking retry loop because already disconnected.",
