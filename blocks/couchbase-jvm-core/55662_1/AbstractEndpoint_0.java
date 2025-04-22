                                + "Authentication Failure.");
                            transitionState(LifecycleState.DISCONNECTED);
                            observable.onError(future.cause());
                        } else if (future.cause() instanceof SSLHandshakeException) {
                            LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                + "SSL Handshake Failure during connect.");
