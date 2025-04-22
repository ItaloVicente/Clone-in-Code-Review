                    });
            }
        })
            .map(new Func1<BucketStreamingResponse, Boolean>() {
                @Override
                public Boolean call(final BucketStreamingResponse response) {
                    response
                        .configs()
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                return s.replace("$HOST", response.host());
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(final String rawConfig) {
                                pushConfig(rawConfig);
