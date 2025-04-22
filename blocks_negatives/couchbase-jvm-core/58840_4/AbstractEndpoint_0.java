                        } else {
                            if (!disconnected) {
                                long delay = env.reconnectDelay().calculate(reconnectAttempt++);
                                TimeUnit delayUnit = env.reconnectDelay().unit();
                                LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                        + "Could not connect to endpoint, retrying with delay " + delay + " "
                                        + delayUnit + ": ", future.cause());
                                if (responseBuffer != null) {
                                    responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR,
