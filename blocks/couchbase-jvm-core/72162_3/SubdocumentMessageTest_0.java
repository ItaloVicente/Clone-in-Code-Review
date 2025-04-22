
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
                new MutationCommand(Mutation.DICT_UPSERT, "spring.class", classFragment, true, true),
                new MutationCommand(Mutation.DICT_UPSERT, "spring.refs", refFragment, true, true));
        MultiMutationResponse mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.content());
        assertTrue(mutationResponse.status().isSuccess());


        SubMultiLookupRequest lookupRequest = new SubMultiLookupRequest(testXAttrKey, bucket(),
                new LookupCommand(Lookup.GET, "spring.class", true),
                new LookupCommand(Lookup.GET, "spring.refs", true));
        MultiLookupResponse lookupResponse = cluster().<MultiLookupResponse>send(lookupRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.content());
        assertTrue(lookupResponse.status().isSuccess());

        mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommand(Mutation.DELETE, "spring.class", true),
                new MutationCommand(Mutation.DELETE, "spring.refs",  true));
        mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.content());
        assertTrue(mutationResponse.status().isSuccess());
    }

    @Ignore
    @Test
    public void shouldNotBeAbleToAccessMultipleXAttrsInDifferentNameSpaces() {
        ByteBuf classFragment = Unpooled.copiedBuffer("\"SomeClass\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(classFragment);
        ByteBuf modelsFragment = Unpooled.copiedBuffer("\"models\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(modelsFragment);


        SubMultiMutationRequest mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommand(Mutation.DICT_UPSERT, "spring.class", classFragment, true, true),
                new MutationCommand(Mutation.DICT_UPSERT, "node.model", modelsFragment, true, true));
        MultiMutationResponse mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.content());
        assertTrue(mutationResponse.status() == ResponseStatus.SUBDOC_XATTR_INVALID_KEY_COMBO);
    }

    @Test
    public void shouldBeAbleToUseMacrosInXAttrsMulti() {
        ByteBuf versionFragment = Unpooled.copiedBuffer("\"${Mutation.CAS}\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(versionFragment);


        SubMultiMutationRequest mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommand(Mutation.DICT_UPSERT, "spring.version", versionFragment, true, true, true));

        MultiMutationResponse mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.content());
        assertTrue(mutationResponse.status().isSuccess());

        SubMultiLookupRequest lookupRequest = new SubMultiLookupRequest(testXAttrKey, bucket(),
                new LookupCommand(Lookup.GET, "spring.version", true));

        MultiLookupResponse lookupResponse = cluster().<MultiLookupResponse>send(lookupRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.content());

        assertTrue(lookupResponse.status().isSuccess());
        assertTrue(lookupResponse.responses().get(0).value().toString(CharsetUtil.UTF_8).length() > 0);

        mutationRequest = new SubMultiMutationRequest(testXAttrKey, bucket(),
                new MutationCommand(Mutation.DELETE, "spring.version", true));
        mutationResponse = cluster().<MultiMutationResponse>send(mutationRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(mutationResponse.content());
        assertTrue(mutationResponse.status().isSuccess());
    }

    @Test
    public void shouldBeAbleToUseMacrosInXAttrsSingle() {
        ByteBuf versionFragment = Unpooled.copiedBuffer("\"${Mutation.CAS}\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(versionFragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest(testXAttrKey, "spring.version", versionFragment, bucket());
        insertRequest.createIntermediaryPath(true);
        insertRequest.attributeAccess(true);
        insertRequest.expandMacros(true);

        SimpleSubdocResponse insertResponse = cluster().<SimpleSubdocResponse>send(insertRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(insertResponse.content());
        assertTrue(insertResponse.status().isSuccess());

        SubGetRequest getRequest = new SubGetRequest(testXAttrKey, "spring.version", bucket());
        getRequest.attributeAccess(true);

        SimpleSubdocResponse lookupResponse = cluster().<SimpleSubdocResponse>send(getRequest).toBlocking().single();
        ReferenceCountUtil.releaseLater(lookupResponse.content());
        assertTrue(lookupResponse.status().isSuccess());
        assertTrue(lookupResponse.content().toString(CharsetUtil.UTF_8).length() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToUseMacrosWhenNotAccessingXAttrs() {
        ByteBuf versionFragment = Unpooled.copiedBuffer("\"${Mutation.CAS}\"", CharsetUtil.UTF_8);
        ReferenceCountUtil.releaseLater(versionFragment);

        SubDictAddRequest insertRequest = new SubDictAddRequest(testXAttrKey, "spring.version", versionFragment, bucket());
        insertRequest.createIntermediaryPath(true);
        insertRequest.expandMacros(true);
    }

