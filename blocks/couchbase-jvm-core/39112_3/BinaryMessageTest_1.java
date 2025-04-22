        final String key = "remove-key-cas";
        final String content = "Hello World!";
        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false, null);

        final UpsertRequest upsert = new UpsertRequest(document, bucket());
        final UpsertResponse upsertResponse = cluster().<UpsertResponse>send(upsert).toBlocking().single();
