        final UpsertRequest upsert = new UpsertRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket());
        final UpsertResponse response = cluster().<UpsertResponse>send(new Func0<CouchbaseRequest>() {
            @Override
            public CouchbaseRequest call() {
                return upsert;
            }
        }).toBlocking().single();
