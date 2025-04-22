                return cluster().send(new BucketStreamingRequest(VERBOSE_PATH, name, password));
            }
        })
        .map(new Func1<BucketStreamingResponse, Boolean>() {
            @Override
            public Boolean call(final BucketStreamingResponse response) {
                response
                    .configs()
                    .map(new Func1<String, String>() {
