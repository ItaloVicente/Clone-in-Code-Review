        final String key = "insert-key";
        final String content = "Hello World!";
        final CoreDocument document = new CoreDocument(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), 0, 0, 0, false, null);

        final InsertRequest firstInsert = new InsertRequest(document, bucket());
        final InsertResponse firstResponse = cluster().<InsertResponse>send(firstInsert).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, firstResponse.status());
