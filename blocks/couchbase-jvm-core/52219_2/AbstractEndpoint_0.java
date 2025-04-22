                                    if (!disconnected) {
                                        doConnect(observable);
                                    } else {
                                        disconnect();
                                        LOGGER.debug("{}Explicitly breaking retry loop because already disconnected.",
                                            logIdent(channel, AbstractEndpoint.this));
                                    }
