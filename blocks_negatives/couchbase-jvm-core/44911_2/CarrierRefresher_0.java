                    public void call() {
                        final InetAddress hostname = config.nodes().get(0).hostname();
                        cluster()
                            .<GetBucketConfigResponse>send(new GetBucketConfigRequest(config.name(), hostname))
                            .subscribe(new Subscriber<GetBucketConfigResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    LOGGER.debug("Error while loading tainted config, ignoring", e);
                                }

                                @Override
                                public void onNext(GetBucketConfigResponse res) {
                                    ByteBuf content = res.content();
                                    if (res.status().isSuccess() && content!= null && content.readableBytes() > 0) {
                                        String rawConfig = content
                                            .toString(CharsetUtil.UTF_8)
                                            .replace("$HOST", hostname.getHostName())
                                            .trim();
                                        if (rawConfig.startsWith("{")) {
                                            provider().proposeBucketConfig(res.bucket(), rawConfig);
                                        }
                                    }
                                    if (content != null && content.refCnt() > 0) {
                                        content.release();
                                    }
                                }
                            });
