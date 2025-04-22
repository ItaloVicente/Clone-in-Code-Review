                        if(future.cause().getMessage() == "Auth Failure") {
                            LOGGER.warn("Authentication failure against: " + future.channel().remoteAddress());
                            transitionState(LifecycleState.DISCONNECTED);
                            observable.onError(future.cause());
                        } else {
                            long delay = reconnectDelay();
                            LOGGER.warn("Could not connect to endpoint, retrying with delay " + delay + "ms: "
                                    + future.channel().remoteAddress(), future.cause());
                            transitionState(LifecycleState.CONNECTING);
                            future.channel().eventLoop().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    connect();
                                }
                            }, delay, TimeUnit.MILLISECONDS);
                        }
