
        ReplaceRequest insert = new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket());
        assertEquals(ResponseStatus.NOT_EXISTS, cluster().<ReplaceResponse>send(insert).toBlocking().single().status());

        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("insert content", CharsetUtil.UTF_8), bucket());
        ReplaceResponse response = cluster().<UpsertResponse>send(upsert)
            .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                @Override
                public Observable<ReplaceResponse> call(UpsertResponse response) {
                 return cluster().send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 24234234L, bucket()));
                }
            }).toBlocking().single();

        assertEquals(ResponseStatus.EXISTS, response.status());
