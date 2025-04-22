                        })
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                LOGGER.debug("Error while refreshing bucket config, ignoring.", e);
                            }

                            @Override
                            public void onNext(String rawConfig) {
                                provider().proposeBucketConfig(config.name(), rawConfig);
                            }
                        });
