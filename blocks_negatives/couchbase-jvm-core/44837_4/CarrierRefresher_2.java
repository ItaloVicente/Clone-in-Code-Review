                            @Override
                            public void onNext(String rawConfig) {
                                if (rawConfig.startsWith("{")) {
                                    provider().proposeBucketConfig(config.name(), rawConfig);
                                }
                            }
                        });
