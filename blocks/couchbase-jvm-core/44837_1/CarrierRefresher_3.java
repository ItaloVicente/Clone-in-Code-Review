                    refreshSequence.subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            LOGGER.debug("Completed refreshing config for bucket \"{}\"", bucketName);
                        }

                        @Override
                        public void onError(Throwable e) {
                            LOGGER.debug("Error while refreshing bucket config, ignoring.", e);
                        }

                        @Override
                        public void onNext(String rawConfig) {
                            if (rawConfig.startsWith("{")) {
                                provider().proposeBucketConfig(config.name(), rawConfig);
