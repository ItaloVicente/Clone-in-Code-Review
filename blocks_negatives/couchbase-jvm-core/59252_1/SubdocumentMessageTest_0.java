    @Test
    public void shouldApplyAllMultiMutationsAndReleaseCommandFragments() {
        ByteBuf counterFragment = Unpooled.copiedBuffer("-404", CharsetUtil.UTF_8);
        counterFragment.retain();

        ByteBuf stringFragment = Unpooled.copiedBuffer("\"mutated\"", CharsetUtil.UTF_8);
        stringFragment.retain(2);

        ByteBuf arrayInsertedFragment = Unpooled.copiedBuffer("\"inserted\"", CharsetUtil.UTF_8);
        ByteBuf arrayFirstFragment = Unpooled.copiedBuffer("\"first\"", CharsetUtil.UTF_8);
        ByteBuf arrayLastFragment = Unpooled.copiedBuffer("\"last\"", CharsetUtil.UTF_8);
        ByteBuf uniqueFragment = Unpooled.copiedBuffer("\"unique\"", CharsetUtil.UTF_8);

        SubMultiMutationRequest request = new SubMultiMutationRequest(testSubKey, bucket(),
                new MutationCommand(Mutation.COUNTER, "counter", counterFragment, false),
                new MutationCommand(Mutation.COUNTER, "another.counter", counterFragment, true),
                new MutationCommand(Mutation.DICT_ADD, "sub.value2", stringFragment),
                new MutationCommand(Mutation.DICT_UPSERT, "sub.value3", stringFragment),
                new MutationCommand(Mutation.REPLACE, "value", stringFragment),
                new MutationCommand(Mutation.ARRAY_INSERT, "sub.array[1]", arrayInsertedFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_FIRST, "sub.array", arrayFirstFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_LAST, "sub.array", arrayLastFragment),
                new MutationCommand(Mutation.ARRAY_ADD_UNIQUE, "sub.array", uniqueFragment),
                new MutationCommand(Mutation.DELETE, "sub.value")
        );
        MultiMutationResponse response = cluster().<MultiMutationResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, response.status());
        assertEquals(Unpooled.EMPTY_BUFFER, response.content());
        assertEquals(-1, response.firstErrorIndex());
        assertEquals(ResponseStatus.SUCCESS, response.firstErrorStatus());

        assertEquals(0, stringFragment.refCnt());
        assertEquals(0, counterFragment.refCnt());
        assertEquals(0, arrayInsertedFragment.refCnt());
        assertEquals(0, arrayFirstFragment.refCnt());
        assertEquals(0, arrayLastFragment.refCnt());
        assertEquals(0, uniqueFragment.refCnt());

        String expected = "{\"value\":\"mutated\", +
-                \"sub\":{" +
                ",\"value2\":\"mutated\"" +
                ",\"value3\":\"mutated\"}, +
-                \"counter\":-404, +
-                \"another\":{\"counter\":-404}}";
        assertMutation(testSubKey, expected);
    }

    @Test
    public void shouldFailSomeMultiMutationsAndReleaseCommandFragments() {
        ByteBuf counterFragment = Unpooled.copiedBuffer("-404", CharsetUtil.UTF_8);
        counterFragment.retain();

        ByteBuf stringFragment = Unpooled.copiedBuffer("\"mutated\"", CharsetUtil.UTF_8);
        stringFragment.retain(2);

        ByteBuf arrayInsertedFragment = Unpooled.copiedBuffer("\"inserted\"", CharsetUtil.UTF_8);
        ByteBuf arrayFirstFragment = Unpooled.copiedBuffer("\"first\"", CharsetUtil.UTF_8);
        ByteBuf arrayLastFragment = Unpooled.copiedBuffer("\"last\"", CharsetUtil.UTF_8);
        ByteBuf uniqueFragment = Unpooled.copiedBuffer("\"unique\"", CharsetUtil.UTF_8);

        SubMultiMutationRequest request = new SubMultiMutationRequest(testSubKey, bucket(),
                new MutationCommand(Mutation.COUNTER, "counter", counterFragment, false),
                new MutationCommand(Mutation.COUNTER, "another.counter", counterFragment, true),
                new MutationCommand(Mutation.DICT_ADD, "sub.value2", stringFragment),
                new MutationCommand(Mutation.DICT_UPSERT, "sub.value3", stringFragment),
                new MutationCommand(Mutation.REPLACE, "value", stringFragment),
                new MutationCommand(Mutation.ARRAY_INSERT, "sub.array[5]", arrayInsertedFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_FIRST, "sub.array", arrayFirstFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_LAST, "sub.array", arrayLastFragment),
                new MutationCommand(Mutation.ARRAY_ADD_UNIQUE, "sub.array", uniqueFragment),
                new MutationCommand(Mutation.DELETE, "path.not.found")
        );
        MultiMutationResponse response = cluster().<MultiMutationResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.SUBDOC_MULTI_PATH_FAILURE, response.status());
        assertEquals(Unpooled.EMPTY_BUFFER, response.content());
        assertEquals(5, response.firstErrorIndex());
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, response.firstErrorStatus());

        assertEquals(0, stringFragment.refCnt());
        assertEquals(0, counterFragment.refCnt());
        assertEquals(0, arrayInsertedFragment.refCnt());
        assertEquals(0, arrayFirstFragment.refCnt());
        assertEquals(0, arrayLastFragment.refCnt());
        assertEquals(0, uniqueFragment.refCnt());

        String expected = jsonContent;
        assertMutation(testSubKey, expected);
    }

    @Test
    public void shouldFailAllMultiMutationsAndReleaseCommandFragments() {
        ByteBuf counterFragment = Unpooled.copiedBuffer("-404", CharsetUtil.UTF_8);
        ByteBuf stringFragment = Unpooled.copiedBuffer("\"mutated\"", CharsetUtil.UTF_8);
        ByteBuf arrayFirstFragment = Unpooled.copiedBuffer("\"first\"", CharsetUtil.UTF_8);

        SubMultiMutationRequest request = new SubMultiMutationRequest(testInsertionSubKey, bucket(),
                new MutationCommand(Mutation.COUNTER, "counter", counterFragment, false),
                new MutationCommand(Mutation.DICT_UPSERT, "sub.value3", stringFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_FIRST, "sub.array", arrayFirstFragment),
                new MutationCommand(Mutation.DELETE, "some.paht")
        );
        MultiMutationResponse response = cluster().<MultiMutationResponse>send(request).toBlocking().single();
        assertEquals(ResponseStatus.NOT_EXISTS, response.status());
        assertEquals(Unpooled.EMPTY_BUFFER, response.content());
        assertEquals(-1, response.firstErrorIndex());
        assertEquals(ResponseStatus.FAILURE, response.firstErrorStatus());

        assertEquals(0, stringFragment.refCnt());
        assertEquals(0, counterFragment.refCnt());
        assertEquals(0, arrayFirstFragment.refCnt());
    }
