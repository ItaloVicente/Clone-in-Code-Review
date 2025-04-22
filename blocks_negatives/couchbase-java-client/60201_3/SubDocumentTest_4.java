        DocumentFragment<String> fragment = DocumentFragment.create(key, "sub", "arrayInsert");
        DocumentFragment<String> result = ctx.bucket().addUniqueIn(fragment, false, PersistTo.NONE, ReplicateTo.NONE);

        assertNotNull(result);
        assertNotEquals(fragment.cas(), result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        assertEquals(1, storedArray.size());
        assertEquals("arrayInsert", storedArray.getString(0));
