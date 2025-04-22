                        if (bootstrapping) {
                            connect(false).subscribe(new Subscriber<LifecycleState>() {
                                @Override
                                public void onCompleted() {}

                                @Override
                                public void onNext(LifecycleState lifecycleState) {}

                                @Override
                                public void onError(Throwable e) {
                                    LOGGER.warn("Error during reconnect: ", e);
                                }
                            });
                        } else if (!disconnected && !isTransient) {
