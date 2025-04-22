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
