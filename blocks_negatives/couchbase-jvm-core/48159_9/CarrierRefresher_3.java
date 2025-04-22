            }
        })
        .map(new Func1<GetBucketConfigResponse, String>() {
            @Override
            public String call(GetBucketConfigResponse response) {
                String raw = response.content().toString(CharsetUtil.UTF_8).trim();
                if (response.content().refCnt() > 0) {
                    response.content().release();
