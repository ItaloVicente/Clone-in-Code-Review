                            node.disconnect().subscribe(new Subscriber<LifecycleState>() {
                                @Override
                                public void onCompleted() {}

                                @Override
                                public void onError(Throwable e) {
                                    LOGGER.warn("Got error during node disconnect.", e);
                                }

                                @Override
                                public void onNext(LifecycleState lifecycleState) {}
                            });
