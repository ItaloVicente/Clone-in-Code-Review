                        }).subscribe(new Action1<String>() {
                        @Override
                        public void call(String rawConfig) {
                            provider().proposeBucketConfig(config.name(), rawConfig);
                        }
                    });
