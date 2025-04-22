    @Test
    public void testArrayInsertMultiValuesVararg() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayInsertAll("array[1]", "a", "b", 123, "d")
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        assertEquals(7, storedArray.size());
        assertEquals("1", storedArray.getString(0));
        assertEquals("a", storedArray.getString(1));
        assertEquals("b", storedArray.getString(2));
        assertEquals(123, storedArray.getInt(3).intValue());
        assertEquals("d", storedArray.getString(4));
        assertEquals(2, storedArray.getInt(5).intValue());
        assertEquals(true, storedArray.getBoolean(6));
    }

    @Test
    public void testArrayInsertMultiValuesCollection() {
        List<?> values = Arrays.asList("a", "b", 123, "d");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayInsertAll("array[1]", values)
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        assertEquals(7, storedArray.size());
        assertEquals("1", storedArray.getString(0));
        assertEquals("a", storedArray.getString(1));
        assertEquals("b", storedArray.getString(2));
        assertEquals(123, storedArray.getInt(3).intValue());
        assertEquals("d", storedArray.getString(4));
        assertEquals(2, storedArray.getInt(5).intValue());
        assertEquals(true, storedArray.getBoolean(6));
    }

