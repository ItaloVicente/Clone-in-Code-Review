        final String key = "upsert-key";
        final String content = "Hello World!";
        final UpsertResponse response = cluster().<UpsertResponse>send(new RequestFactory() {
            @Override
            public CouchbaseRequest call() {
                return new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket());
            }
        }).toBlocking().single();
