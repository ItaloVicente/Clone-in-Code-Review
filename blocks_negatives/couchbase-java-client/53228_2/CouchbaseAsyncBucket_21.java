        return core.<CloseBucketResponse>send(new CloseBucketRequest(bucket))
            .map(new Func1<CloseBucketResponse, Boolean>() {
                @Override
                public Boolean call(CloseBucketResponse response) {
                    return response.status().isSuccess();
                }
            });
