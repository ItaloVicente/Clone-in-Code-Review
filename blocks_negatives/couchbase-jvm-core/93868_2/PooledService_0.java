                    try {
                        if (disconnect) {
                            RetryHelper.retryOrCancel(env, request, responseBuffer);
                        } else {
                            endpoint.send(request);
                            endpoint.send(SignalFlush.INSTANCE);

                            synchronized (epMutex) {
                                endpoints.add(endpoint);
                                endpointStates.register(endpoint, endpoint);
                                LOGGER.debug(logIdent(hostname, PooledService.this)
                                        + "New number of endpoints is {}", endpoints.size());
                            }
                        }
                    } finally {
                        pendingRequests--;
