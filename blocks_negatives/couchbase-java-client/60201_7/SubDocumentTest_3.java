    @Test(expected = IllegalArgumentException.class)
    public void testArrayInsertOnEmptyPath() {
        DocumentFragment<String> fragment = DocumentFragment.create(key, "", "arrayInsert");
        DocumentFragment<String> result = ctx.bucket().arrayInsertIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Test(expected = CASMismatchException.class)
    public void testArrayInsertWithBadCas() {
        DocumentFragment<Long> fragment = DocumentFragment.create(key, "int", null, 1234L);
        ctx.bucket().arrayInsertIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }
