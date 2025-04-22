                public void call(final BucketConfig config) {
                    InetAddress hostname = config.nodes().get(0).hostname();

                    cluster()
                        .<GetBucketConfigResponse>send(new GetBucketConfigRequest(config.name(), hostname))
                        .map(new Func1<GetBucketConfigResponse, String>() {
                            @Override
                            public String call(GetBucketConfigResponse response) {
                                String raw = response.content().toString(CharsetUtil.UTF_8);
                                return raw.replace("$HOST", response.hostname().getHostName());
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String rawConfig) {
                                provider().proposeBucketConfig(config.name(), rawConfig);
                            }
                        });
