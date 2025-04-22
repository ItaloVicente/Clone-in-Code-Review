                            LOGGER.warn(
                                "{}Could not connect to endpoint, retrying with delay " + delay + " "
                                    + delayUnit + ": ",
                                system(logIdent(channel, AbstractEndpoint.this)),
                                future.cause()
                            );
