
    @Test
    public void shouldBeAbleToAccessXAttrs() {
        String subPath = "spring.class";
        ByteBuf fragment = Unpooled.copiedBuffer("\"SomeClass\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(fragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest(testXAttrKey, subPath, fragment, bucket());
        insertRequest.attributeAccess(true);
        insertRequest.createIntermediaryPath(true);
        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());
        assertEquals(0, insertResponse.content().readableBytes());

        SubGetRequest getRequest = new SubGetRequest(testXAttrKey, "spring.class", bucket());
        getRequest.attributeAccess(true);
        SimpleSubdocResponse lookupResponse = cluster().<SimpleSubdocResponse>send(getRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.content());
        assertTrue(lookupResponse.status().isSuccess());
        assertEquals(lookupResponse.content().toString(CharsetUtil.UTF_8) , "\"SomeClass\"");


        SubDeleteRequest deleteRequest = new SubDeleteRequest(testXAttrKey, subPath, bucket());
        deleteRequest.attributeAccess(true);
        SimpleSubdocResponse deleteResponse = cluster().<SimpleSubdocResponse>send(deleteRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(deleteResponse.content());
        assertTrue(deleteResponse.status().isSuccess());
        assertEquals(0, deleteResponse.content().readableBytes());


        getRequest = new SubGetRequest(testXAttrKey, "spring.class", bucket());
        getRequest.attributeAccess(true);
        lookupResponse = cluster().<SimpleSubdocResponse>send(getRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.content());
        assertTrue(lookupResponse.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND);
    }

    @Test
    public void shouldBeAbleToAccessMultipleXAttrsInSameNameSpace() {
        ByteBuf classFragment = Unpooled.copiedBuffer("\"SomeClass\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(classFragment);
        ByteBuf refFragment = Unpooled.copiedBuffer("\"ids\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(refFragment);

        SubMultiMutationRequest mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommandBuilder(Mutation.DICT_UPSERT, "spring.class")
                .fragment(classFragment).createIntermediaryPath(true).attributeAccess(true).build(),
                new MutationCommandBuilder(Mutation.DICT_UPSERT, "spring.refs")
                .fragment(refFragment).createIntermediaryPath(true).attributeAccess(true).build());
        MultiMutationResponse mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.responses().get(0).value());
        ReferenceCountUtil.releaseLater(mutationResponse.responses().get(1).value());
        assertTrue(mutationResponse.status().isSuccess());

        SubMultiLookupRequest lookupRequest = new SubMultiLookupRequest(testXAttrKey, bucket(),
                new LookupCommandBuilder(Lookup.GET, "spring.class")
                      .attributeAccess(true).build(),
                new LookupCommandBuilder(Lookup.GET, "spring.refs")
                        .attributeAccess(true).build());

        MultiLookupResponse lookupResponse = cluster().<MultiLookupResponse>send(lookupRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.responses().get(0).value());
        ReferenceCountUtil.releaseLater(lookupResponse.responses().get(1).value());
        assertTrue(lookupResponse.status().isSuccess());

        mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommandBuilder(Mutation.DELETE, "spring.class").attributeAccess(true).build(),
                new MutationCommandBuilder(Mutation.DELETE, "spring.refs").attributeAccess(true).build());
        mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.responses().get(0).value());
        ReferenceCountUtil.releaseLater(mutationResponse.responses().get(1).value());
        assertTrue(mutationResponse.status().isSuccess());
    }
