    @Test
    public void testMultiMutation() {
        MultiMutationResult mmr = ctx.bucket().mutateIn(JsonDocument.create(key), PersistTo.NONE, ReplicateTo.NONE,
                MutationSpec.replace("sub.value", "replaced"),
                MutationSpec.replace("string", "otherString"),
                MutationSpec.upsert("sub.otherValue", "newValue", false),
                MutationSpec.arrayInsert("array[1]", "v"),
                MutationSpec.addUnique("array", "v2", false),
                MutationSpec.extend("array", "v3", ExtendDirection.BACK, false),
                MutationSpec.counter("int", 1000, false),
                MutationSpec.insert("sub.insert", "inserted", false),
                MutationSpec.remove("boolean")
        );

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
        MultiMutationResult mmr = ctx.bucket().mutateIn(JsonDocument.create(key), PersistTo.NONE, ReplicateTo.NONE,
                MutationSpec.addUnique("addUnique.array", "v", true),
                MutationSpec.counter("counter.newCounter", 100, true),
                MutationSpec.extend("extend.array", "v", ExtendDirection.FRONT, true),
                MutationSpec.insert("insert.sub.entry", "v", true),
                MutationSpec.upsert("upsert.sub.entry", "v", true)
        );

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
        try {
            ctx.bucket().mutateIn(JsonDocument.create(key), PersistTo.NONE, ReplicateTo.NONE,
                    MutationSpec.replace("sub.value", "replaced"),
                    MutationSpec.replace("int", 1024),
                    MutationSpec.upsert("sub.otherValue.deeper", "newValue", false),
                    MutationSpec.replace("secondError", "unreachable"));
            fail("Expected MultiMutationException");
        } catch (MultiMutationException e) {
            assertEquals(2, e.firstFailureIndex());
            assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, e.firstFailureStatus());
            assertNotNull(e.getCause());
            assertTrue(e.getCause().toString(), e.getCause() instanceof  PathNotFoundException);
            assertTrue(e.getCause().toString(), e.getCause().toString().contains("sub.otherValue.deeper"));
            assertEquals(4, e.originalSpec().size());
        }

        assertEquals(testJson, ctx.bucket().get(key).content());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiMutationWithEmptySpecFails() {
        ctx.bucket().mutateIn(JsonDocument.create(key), PersistTo.NONE, ReplicateTo.NONE);
    }

    @Test(expected = NullPointerException.class)
    public void testMultiMutationWithNullSpecFails() {
        ctx.bucket().mutateIn(JsonDocument.create(key), PersistTo.NONE, ReplicateTo.NONE, (MutationSpec[]) null);
    }

    @Test(expected = CASMismatchException.class)
    public void testMultiMutationWithBadCas() {
        ctx.bucket().mutateIn(JsonDocument.create(key, null, 1234L), PersistTo.NONE, ReplicateTo.NONE, MutationSpec.replace("sub", 123));
    }
