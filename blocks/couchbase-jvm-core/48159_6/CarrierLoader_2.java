        return cluster()
            .<GetBucketConfigResponse>send(new GetBucketConfigRequest(bucket, hostname))
            .map(new Func1<GetBucketConfigResponse, String>() {
                @Override
                public String call(GetBucketConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        response.content().release();
                        throw new IllegalStateException("Bucket config response did not return with success.");
                    }

                    LOGGER.debug("Successfully loaded config through carrier.");
                    String content = response.content().toString(CharsetUtil.UTF_8);
