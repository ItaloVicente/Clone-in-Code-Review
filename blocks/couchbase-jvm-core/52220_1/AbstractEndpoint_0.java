                                    if (!disconnected) {
                                        doConnect(observable);
                                    } else {
                                        LOGGER.debug("{}Explicitly breaking retry loop because already disconnected.",
                                            logIdent(channel, AbstractEndpoint.this));
                                        disconnect();
                                    }
