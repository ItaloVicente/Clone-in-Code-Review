        return Buffers.wrapColdWithAutoRelease(
           cluster().<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucket, hostname))
        )
        .map(new Func1<GetBucketConfigResponse, String>() {
            @Override
            public String call(GetBucketConfigResponse response) {
                if (!response.status().isSuccess()) {
