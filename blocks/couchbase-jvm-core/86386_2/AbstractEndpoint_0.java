                            LOGGER.warn(
                                "{}Could not connect to endpoint, retrying with delay " + delay + " "
                                    + delayUnit + ": ",
                                logIdent(channel, AbstractEndpoint.this),
                                future.cause()
                            );
