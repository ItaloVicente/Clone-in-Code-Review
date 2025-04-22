        assertEquals(1000L, result.fragment().longValue());
        assertEquals(1000L, ctx.bucket().get(key).content().getObject("counters").getLong("a").longValue());
    }

    @Test(expected = CASMismatchException.class)
    public void testCounterInWithBadCas() {
        DocumentFragment<Long> delta = DocumentFragment.create(key, "int", 1000L, 1234L);
        ctx.bucket().counterIn(delta, false, PersistTo.NONE, ReplicateTo.NONE);
