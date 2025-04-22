                                                                                    @Override
                                                                                    public Observable<BucketStreamingResponse> call(Throwable throwable) {
                                                                                        return cluster().send(new BucketStreamingRequest(VERBOSE_PATH, name, password));
                                                                                    }
                                                                                }
        )
