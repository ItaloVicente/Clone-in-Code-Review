                        if(future.cause() instanceof AuthenticationException) {
                            LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                + "Authentication Failure.");
                            transitionState(LifecycleState.DISCONNECTED);
                            observable.onError(future.cause());
                        } else if (future.cause() instanceof ClosedChannelException) {
                            LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                + "Generic Failure.");
                            transitionState(LifecycleState.DISCONNECTED);
