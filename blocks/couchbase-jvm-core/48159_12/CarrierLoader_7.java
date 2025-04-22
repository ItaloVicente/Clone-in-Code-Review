            })
            .map(new Func1<GetBucketConfigResponse, String>() {
                @Override
                public String call(GetBucketConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        response.content().release();
                        throw new IllegalStateException("Bucket config response did not return with success.");
                    }
