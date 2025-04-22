        Thread.sleep(2000);

        GetRequest request = new GetRequest(key, bucket);
        assertEquals("Not found", cluster.<GetResponse>send(request).toBlockingObservable().single().content());
    }

    @Test
    public void shouldHandleDoubleInsert() {
        String key = "insert-key";
        String content = "Hello World!";
        InsertRequest insert = new InsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.OK, cluster.<InsertResponse>send(insert).toBlockingObservable().single().status());

        insert = new InsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.EXISTS, cluster.<InsertResponse>send(insert).toBlockingObservable().single().status());
    }

    @Test
    public void shouldReplaceWithoutCAS() {
        final String key = "replace-key";
        final String content = "replace content";

        ReplaceRequest insert = new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.NOT_EXISTS, cluster.<ReplaceResponse>send(insert).toBlockingObservable().single().status());

        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("insert content", CharsetUtil.UTF_8), bucket);
        ReplaceResponse response = cluster.<UpsertResponse>send(upsert)
            .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                @Override
                public Observable<ReplaceResponse> call(UpsertResponse response) {
                    return cluster.send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket));
                }
            }
        ).toBlockingObservable().single();

        assertEquals(ResponseStatus.OK, response.status());
    }

    @Test
    public void shouldReplaceWithCAS() {
        final String key = "replace-key-cas";
        final String content = "replace content";

        ReplaceRequest insert = new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.NOT_EXISTS, cluster.<ReplaceResponse>send(insert).toBlockingObservable().single().status());

        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("insert content", CharsetUtil.UTF_8), bucket);
        ReplaceResponse response = cluster.<UpsertResponse>send(upsert)
                .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                             @Override
                             public Observable<ReplaceResponse> call(UpsertResponse response) {
                                 return cluster.send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 24234234L, bucket));
                             }
                         }
                ).toBlockingObservable().single();

        assertEquals(ResponseStatus.EXISTS, response.status());

