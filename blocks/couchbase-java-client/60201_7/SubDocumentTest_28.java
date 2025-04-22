    @Test
    public void testMultiMutation() {
        DocumentFragment<Mutation> mmr = ctx.bucket()
                .mutateIn(key)
                .replace("sub.value", "replaced")
                .replace("string", "otherString")
                .upsert("sub.otherValue", "newValue", false)
                .arrayInsert("array[1]", "v")
                .addUnique("array", "v2", false)
                .pushBack("array", "v3", false)
                .counter("int", 1000, false)
                .insert("sub.insert", "inserted", false)
                .remove("boolean")
                .doMutate();

        JsonDocument stored = ctx.bucket().get(key);

        assertNotNull(mmr);
        assertNotEquals(0L, mmr.cas());
        assertEquals(stored.cas(), mmr.cas());
        assertEquals(stored.mutationToken(), mmr.mutationToken());

        assertEquals("replaced", stored.content().getObject("sub").getString("value"));
        assertEquals("otherString", stored.content().getString("string"));
        assertEquals("newValue", stored.content().getObject("sub").getString("otherValue"));
        assertEquals(JsonArray.from("1", "v", 2, true, "v2", "v3"), stored.content().getArray("array"));
        assertEquals(1123, stored.content().getInt("int").intValue());
        assertEquals("inserted", stored.content().getObject("sub").getString("insert"));
        assertFalse(stored.content().containsKey("boolean"));
    }

    @Test
    public void testMultiMutationWithCreateParents() {
        DocumentFragment<Mutation> mmr = ctx.bucket()
                .mutateIn(key)
                .addUnique("addUnique.array", "v", true)
                .counter("counter.newCounter", 100, true)
                .pushFront("extend.array", "v", true)
                .insert("insert.sub.entry", "v", true)
                .upsert("upsert.sub.entry", "v", true)
                .doMutate();

        JsonDocument stored = ctx.bucket().get(key);

        assertNotNull(mmr);
        assertNotEquals(0L, mmr.cas());
        assertEquals(stored.cas(), mmr.cas());
        assertEquals(stored.mutationToken(), mmr.mutationToken());

        assertEquals("v", stored.content().getObject("addUnique").getArray("array").getString(0));
        assertEquals(100L, stored.content().getObject("counter").getLong("newCounter").longValue());
        assertEquals("v", stored.content().getObject("extend").getArray("array").getString(0));
        assertEquals("v", stored.content().getObject("insert").getObject("sub").getString("entry"));
        assertEquals("v", stored.content().getObject("upsert").getObject("sub").getString("entry"));
    }

    @Test
    public void testMultiMutationWithFailure() {
        verifyException(ctx.bucket()
                .mutateIn(key)
                .replace("sub.value", "replaced")
                .replace("int", 1024)
                .upsert("sub.otherValue.deeper", "newValue", false)
                .replace("secondError", "unreachable"))
                .doMutate();

        assertThat(caughtException(), instanceOf(MultiMutationException.class));
        MultiMutationException e = caughtException();
        assertEquals(2, e.firstFailureIndex());
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, e.firstFailureStatus());
        assertNotNull(e.getCause());
        assertTrue(e.getCause().toString(), e.getCause() instanceof  PathNotFoundException);
        assertTrue(e.getCause().toString(), e.getCause().toString().contains("sub.otherValue.deeper"));
        assertEquals(4, e.originalSpec().size());

        assertEquals(testJson, ctx.bucket().get(key).content());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiMutationWithEmptySpecFails() {
        ctx.bucket().mutateIn(key).doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testMultiMutationWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .replace("sub", 123)
                .remove("int")
        .doMutate();
    }
