    }

    @Test
    public void shouldReplaceWithMatchingCAS() {
        final String key = "replace-key-cas-match";
        final String content = "replace content";

        ReplaceRequest insert = new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.NOT_EXISTS, cluster.<ReplaceResponse>send(insert).toBlockingObservable().single().status());

        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer("insert content", CharsetUtil.UTF_8), bucket);
        ReplaceResponse response = cluster.<UpsertResponse>send(upsert)
            .flatMap(new Func1<UpsertResponse, Observable<ReplaceResponse>>() {
                @Override
                public Observable<ReplaceResponse> call(UpsertResponse response) {
                    return cluster.send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), response.cas(), bucket));
                }
            }).toBlockingObservable().single();

        assertEquals(ResponseStatus.OK, response.status());
    }

    @Test
    public void shouldRemoveDocumentWithoutCAS() {
        String key = "remove-key";
        String content = "Hello World!";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        assertEquals(ResponseStatus.OK, cluster.<UpsertResponse>send(upsert).toBlockingObservable().single().status());

        RemoveRequest remove = new RemoveRequest(key, bucket);
        assertEquals(ResponseStatus.OK, cluster.<RemoveResponse>send(remove).toBlockingObservable().single().status());
        assertEquals(ResponseStatus.NOT_EXISTS, cluster.<RemoveResponse>send(remove).toBlockingObservable().single().status());
    }

    @Test
    public void shouldRemoveDocumentWithCAS() {
        String key = "remove-key-cas";
        String content = "Hello World!";
        UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket);
        UpsertResponse upsertResponse = cluster.<UpsertResponse>send(upsert).toBlockingObservable().single();
        assertEquals(ResponseStatus.OK, upsertResponse.status());
