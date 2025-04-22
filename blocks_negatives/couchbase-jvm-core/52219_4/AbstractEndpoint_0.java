                            long delay = env.reconnectDelay().calculate(reconnectAttempt++);
                            TimeUnit delayUnit = env.reconnectDelay().unit();
                            LOGGER.warn(logIdent(channel, AbstractEndpoint.this)
                                    + "Could not connect to endpoint, retrying with delay " + delay + " "
                                    + delayUnit + ": ", future.cause());
                            if (responseBuffer != null) {
                                responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, SignalConfigReload.INSTANCE, null);
                            }
                            transitionState(LifecycleState.CONNECTING);
                            future.channel().eventLoop().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    doConnect(observable);
