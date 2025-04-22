                    reconfigure(config).subscribe(new Subscriber<ClusterConfig>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {
                            LOGGER.warn("Received Error during Reconfiguration.", e);
                        }

                        @Override
                        public void onNext(ClusterConfig clusterConfig) {}
                    });
