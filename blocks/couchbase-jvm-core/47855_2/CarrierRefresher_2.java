        return Buffers.wrapColdWithAutoRelease(
            cluster().<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucketName, hostname))
        )
        .doOnNext(new Action1<GetBucketConfigResponse>() {
            @Override
            public void call(GetBucketConfigResponse response) {
                if (!response.status().isSuccess()) {
                    if (response.content() != null && response.content().refCnt() > 0) {
