    @Test
    public void shouldAddDocumentIfSet() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldAddDocumentIfSet", subPath, fragment, bucket());
        insertRequest.createDocument(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldAddDocumentIfSet", bucket())).toBlocking().single();
        assert(response.status() == ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldAccessDeletedDocumentIfSet() {
        String subPath = "xattr.hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldAccessDeletedDocumentIfSet", subPath, fragment, bucket());
        insertRequest.createDocument(true);
        insertRequest.xattr(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldAccessDeletedDocumentIfSet", bucket())).toBlocking().single();
        assert(response.status() == ResponseStatus.SUCCESS);

        SubGetRequest getRequest = new SubGetRequest("shouldAccessDeletedDocumentIfSet", subPath, bucket());
        getRequest.xattr(true);
        getRequest.accessDeleted(true);
        SimpleSubdocResponse getResponse = cluster().<SimpleSubdocResponse>send(getRequest).toBlocking().single();

        assert(getResponse.status() == ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldAccessDeletedDocumentIfSetInMultiPath() {
        String subPath = "spring.class";
        ByteBuf fragment = Unpooled.copiedBuffer("\"test\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldAccessDeletedDocumentIfSetInMultiPath", subPath, fragment, bucket());
        insertRequest.addDocument(true);
        insertRequest.xattr(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldAccessDeletedDocumentIfSetInMultiPath", bucket())).toBlocking().single();
        assert(response.status() == ResponseStatus.SUCCESS);

        SubMultiLookupRequest lookupRequest = new SubMultiLookupRequest("shouldAccessDeletedDocumentIfSetInMultiPath", bucket(),
                SubMultiLookupDocOptionsBuilder.builder().accessDeleted(true),
                new LookupCommandBuilder(Lookup.GET, "spring.class")
                        .xattr(true).build());
        MultiLookupResponse lookupResponse = cluster().<MultiLookupResponse>send(lookupRequest).toBlocking().single();
        assertTrue(lookupResponse.status().isSuccess());
    }

