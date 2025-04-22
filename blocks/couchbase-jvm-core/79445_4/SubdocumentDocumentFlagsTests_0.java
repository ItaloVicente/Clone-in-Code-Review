        assertEquals(insertResponse.status(), ResponseStatus.NOT_EXISTS);
    }

    @Test
    public void shouldInsertDocumentIfSet() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldInsertDocumentIfSet", subPath, fragment, bucket());
        insertRequest.insertDocument(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldInsertDocumentIfSet", bucket())).toBlocking().single();
        assertEquals(response.status(), ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldFailOnInsertDocumentIfSetOnDocExists() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldFailOnInsertDocumentIfSetOnDocExists", subPath, fragment, bucket());
        insertRequest.insertDocument(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        SubDictAddRequest insertRequest2 = new SubDictAddRequest("shouldFailOnInsertDocumentIfSetOnDocExists", subPath, Unpooled.EMPTY_BUFFER, bucket());
        insertRequest2.insertDocument(false);
        SimpleSubdocResponse insertResponse2 = cluster().<SimpleSubdocResponse>send(insertRequest2).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse2.content());
        assertFalse(insertResponse2.status().isSuccess());
        RemoveResponse removeResponse = cluster().<RemoveResponse>send(new RemoveRequest("shouldFailOnInsertDocumentIfSetOnDocExists", bucket())).toBlocking().single();
        assertEquals(removeResponse.status(), ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldAccessDeletedDocumentIfSet() {
        String subPath = "_hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldAccessDeletedDocumentIfSet", subPath, fragment, bucket());
        insertRequest.createDocument(true);
        insertRequest.xattr(true);
        insertRequest.createIntermediaryPath(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldAccessDeletedDocumentIfSet", bucket())).toBlocking().single();
        assertEquals(response.status(), ResponseStatus.SUCCESS);

        SubGetRequest getRequest = new SubGetRequest("shouldAccessDeletedDocumentIfSet", subPath, bucket());
        getRequest.xattr(true);
        getRequest.accessDeleted(true);
        SimpleSubdocResponse getResponse = cluster().<SimpleSubdocResponse>send(getRequest).toBlocking().single();
        assertEquals(getResponse.status(), ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldAccessDeletedDocumentIfSetInMultiPath() {
        String subPath = "_class";
        ByteBuf fragment = Unpooled.copiedBuffer("\"test\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldAccessDeletedDocumentIfSetInMultiPath", subPath, fragment, bucket());
        insertRequest.insertDocument(true);
        insertRequest.xattr(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldAccessDeletedDocumentIfSetInMultiPath", bucket())).toBlocking().single();
        assertEquals(response.status(), ResponseStatus.SUCCESS);

        SubMultiLookupRequest lookupRequest = new SubMultiLookupRequest("shouldAccessDeletedDocumentIfSetInMultiPath", bucket(),
                SubMultiLookupDocOptionsBuilder.builder().accessDeleted(true),
                new LookupCommandBuilder(Lookup.GET, "_class")
                        .xattr(true).build());
        MultiLookupResponse lookupResponse = cluster().<MultiLookupResponse>send(lookupRequest).toBlocking().single();
        assertTrue(lookupResponse.status().isSuccess());
