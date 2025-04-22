                if (disconnect) {
                    RetryHelper.retryOrCancel(ctx.environment(), request, ctx.responseRingBuffer());
                    LOGGER.debug(logIdent(hostname, AbstractOnDemandService.this)
                        + "Initializing disconnect on Endpoint.");
                    endpoint.disconnect().subscribe(new Subscriber<LifecycleState>() {
                        @Override
                        public void onCompleted() { }

                        @Override
                        public void onError(Throwable e) {
                            LOGGER.warn("Error while disconnecting endpoint.", e);
                        }

                        @Override
                        public void onNext(LifecycleState lifecycleState) { }
                    });
                } else {
                    endpoint.send(request);
                    endpoint.send(SignalFlush.INSTANCE);
                }
