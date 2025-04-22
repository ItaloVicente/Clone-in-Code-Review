    @Test
    public void testArrayPrependMultiValuesVararg() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayPrependAll("array", "a", "b", 123, "d")
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        assertEquals(storedArray.toString(), 7, storedArray.size());
        assertEquals("a", storedArray.getString(0));
        assertEquals("b", storedArray.getString(1));
        assertEquals(123, storedArray.getInt(2).intValue());
        assertEquals("d", storedArray.getString(3));
        assertEquals("1", storedArray.getString(4));
        assertEquals(2, storedArray.getInt(5).intValue());
        assertEquals(true, storedArray.getBoolean(6));
    }

    @Test
    public void testArrayPrependMultiValuesCollection() {
        List<?> values = Arrays.asList("a", "b", 123, "d");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayPrependAll("array", values, false)
                .arrayPrependAll("array2", values, true)
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        assertEquals(7, storedArray.size());
        assertEquals("a", storedArray.getString(0));
        assertEquals("b", storedArray.getString(1));
        assertEquals(123, storedArray.getInt(2).intValue());
        assertEquals("d", storedArray.getString(3));
        assertEquals("1", storedArray.getString(4));
        assertEquals(2, storedArray.getInt(5).intValue());
        assertEquals(true, storedArray.getBoolean(6));

        storedArray = ctx.bucket().get(key).content().getArray("array2");
        assertEquals(values, storedArray.toList());
    }

    @Test
    public void testArrayAppendMultiValuesVararg() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayAppendAll("array", "a", "b", 123, "d")
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        System.out.println(storedArray);
        assertEquals(7, storedArray.size());
        assertEquals("1", storedArray.getString(0));
        assertEquals(2, storedArray.getInt(1).intValue());
        assertEquals(true, storedArray.getBoolean(2));
        assertEquals("a", storedArray.getString(3));
        assertEquals("b", storedArray.getString(4));
        assertEquals(123, storedArray.getInt(5).intValue());
        assertEquals("d", storedArray.getString(6));
    }

    @Test
    public void testArrayAppendMultiValuesCollection() {
        List<?> values = Arrays.asList("a", "b", 123, "d");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayAppendAll("array", values, false)
                .arrayAppendAll("array2", values, true)
                .doMutate();

        assertNotNull(result);
        assertNotEquals(0L, result.cas());
        JsonArray storedArray = ctx.bucket().get(key).content().getArray("array");
        System.out.println(storedArray);
        assertEquals(7, storedArray.size());
        assertEquals("1", storedArray.getString(0));
        assertEquals(2, storedArray.getInt(1).intValue());
        assertEquals(true, storedArray.getBoolean(2));
        assertEquals("a", storedArray.getString(3));
        assertEquals("b", storedArray.getString(4));
        assertEquals(123, storedArray.getInt(5).intValue());
        assertEquals("d", storedArray.getString(6));

        storedArray = ctx.bucket().get(key).content().getArray("array2");
        assertEquals(values, storedArray.toList());
    }

