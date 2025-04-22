                public Boolean call(BucketStreamingResponse response) {
                    response.configs().subscribe(new Action1<String>() {
                        @Override
                        public void call(final String rawConfig) {
                            pushConfig(rawConfig);
                        }
                    });
