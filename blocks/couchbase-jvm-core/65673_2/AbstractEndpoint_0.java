                                    + "Socket connect took longer than specified timeout.");
                            transitionState(LifecycleState.DISCONNECTED);
                            observable.onError(future.cause());
                        } else if (future.cause() instanceof ConnectException) {
                            LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                    + "Could not connect to remote socket.");
