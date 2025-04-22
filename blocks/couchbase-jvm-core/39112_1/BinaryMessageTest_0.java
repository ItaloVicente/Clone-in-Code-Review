        final String key = "remove-key-cas";
        final String content = "Hello World!";
        final CoreDocument document = new CoreDocument(Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false);

        final UpsertRequest upsert = new UpsertRequest(key, document, bucket());
        final UpsertResponse upsertResponse = cluster().<UpsertResponse>send(upsert).toBlocking().single();
