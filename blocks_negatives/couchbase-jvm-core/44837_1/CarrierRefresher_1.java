                    InetAddress hostname = config.nodes().get(0).hostname();

                    cluster()
                        .<GetBucketConfigResponse>send(new GetBucketConfigRequest(config.name(), hostname))
                        .filter(new Func1<GetBucketConfigResponse, Boolean>() {
                            @Override
                            public Boolean call(GetBucketConfigResponse response) {
                                boolean good = response.status().isSuccess() && response.content() != null;
                                if (!good) {
                                    if (response.content() != null) {
                                        response.content().release();
                                    }
                                }
                                return good;
                            }
                        })
                        .map(new Func1<GetBucketConfigResponse, String>() {
                            @Override
                            public String call(GetBucketConfigResponse response) {
                                String raw = response.content().toString(CharsetUtil.UTF_8).trim();
                                response.content().release();
                                return raw.replace("$HOST", response.hostname().getHostName());
                            }
                        })
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
