    @Test
    public void shouldCreateDocumentIfSet() {
        String subPath = "hello";
        ByteBuf fragment = Unpooled.copiedBuffer("\"world\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest("shouldCreateDocumentIfSet", subPath, fragment, bucket());
        insertRequest.createDocument(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        RemoveResponse response = cluster().<RemoveResponse>send(new RemoveRequest("shouldCreateDocumentIfSet", bucket())).toBlocking().single();
        assertEquals(response.status(), ResponseStatus.SUCCESS);
    }

