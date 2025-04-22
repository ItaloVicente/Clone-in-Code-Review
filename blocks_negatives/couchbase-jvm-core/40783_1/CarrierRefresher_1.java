                public Observable<GetBucketConfigResponse> call(BucketConfig config) {
                    GetBucketConfigRequest req = new GetBucketConfigRequest(config.name(), config.nodes().get(0).hostname());
                    return cluster().send(req);
                }
            }).subscribe(new Action1<GetBucketConfigResponse>() {
                @Override
                public void call(GetBucketConfigResponse res) {
                    provider().proposeBucketConfig(res.bucket(), res.content().toString(CharsetUtil.UTF_8));
