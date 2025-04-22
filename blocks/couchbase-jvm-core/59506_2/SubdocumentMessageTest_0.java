    @Test
    public void shouldApplyAllMultiMutationsAndReleaseCommandFragments() {
        ByteBuf counterFragment = Unpooled.copiedBuffer("-404", CharsetUtil.UTF_8);
        counterFragment.retain(2);

        ByteBuf stringFragment = Unpooled.copiedBuffer("\"mutated\"", CharsetUtil.UTF_8);
        stringFragment.retain(2);

        ByteBuf arrayInsertedFragment = Unpooled.copiedBuffer("\"inserted\"", CharsetUtil.UTF_8);
        ByteBuf arrayFirstFragment = Unpooled.copiedBuffer("\"first\"", CharsetUtil.UTF_8);
        ByteBuf arrayLastFragment = Unpooled.copiedBuffer("\"last\"", CharsetUtil.UTF_8);
        ByteBuf uniqueFragment = Unpooled.copiedBuffer("\"unique\"", CharsetUtil.UTF_8);

        MutationCommand[] commands = new MutationCommand[] {
                new MutationCommand(Mutation.COUNTER, "counter", counterFragment, false),
                new MutationCommand(Mutation.COUNTER, "another.counter", counterFragment, true),
                new MutationCommand(Mutation.COUNTER, "another.counter", counterFragment, false),
                new MutationCommand(Mutation.DICT_ADD, "sub.value2", stringFragment),
                new MutationCommand(Mutation.DICT_UPSERT, "sub.value3", stringFragment),
                new MutationCommand(Mutation.REPLACE, "value", stringFragment),
                new MutationCommand(Mutation.ARRAY_INSERT, "sub.array[1]", arrayInsertedFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_FIRST, "sub.array", arrayFirstFragment),
                new MutationCommand(Mutation.ARRAY_PUSH_LAST, "sub.array", arrayLastFragment),
                new MutationCommand(Mutation.ARRAY_ADD_UNIQUE, "sub.array", uniqueFragment),
                new MutationCommand(Mutation.DELETE, "sub.value")
        };

        SubMultiMutationRequest request = new SubMultiMutationRequest(testSubKey, bucket(), commands);
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

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            MutationCommand command = commands[i];
            MultiResult result = response.responses().get(i);
            assertEquals(command.path(), result.path());
            assertEquals(command.mutation(), result.operation());

            sb.append('\n').append(result);
            ReferenceCountUtil.releaseLater(result.value());
        }
        if (sb.length() > 1) sb.deleteCharAt(0);

        String expectedResponse = "COUNTER(counter): SUCCESS = -404" +
                "\nCOUNTER(another.counter): SUCCESS = -404" +
                "\nCOUNTER(another.counter): SUCCESS = -808" +
                "\nDICT_ADD(sub.value2): SUCCESS" +
                "\nDICT_UPSERT(sub.value3): SUCCESS" +
                "\nREPLACE(value): SUCCESS" +
                "\nARRAY_INSERT(sub.array[1]): SUCCESS" +
                "\nARRAY_PUSH_FIRST(sub.array): SUCCESS" +
                "\nARRAY_PUSH_LAST(sub.array): SUCCESS" +
                "\nARRAY_ADD_UNIQUE(sub.array): SUCCESS" +
                "\nDELETE(sub.value): SUCCESS";
        assertEquals(expectedResponse, sb.toString());

        String expected = "{\"value\":\"mutated\"," +
                "\"sub\":{" +
                "\"array\":[\"first\",\"array1\",\"inserted\",2,true,\"last\",\"unique\"]" +
                ",\"value2\":\"mutated\"" +
                ",\"value3\":\"mutated\"}," +
                "\"counter\":-404," +
                "\"another\":{\"counter\":-808}}";
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
        assertEquals(0, response.responses().size());

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
        assertEquals(0, response.responses().size());

        assertEquals(0, stringFragment.refCnt());
        assertEquals(0, counterFragment.refCnt());
        assertEquals(0, arrayFirstFragment.refCnt());
    }
