        DocumentFragment<String> fragment = DocumentFragment.create(key, "array", "arrayInsert");
        DocumentFragment<String> result = ctx.bucket().arrayInsertIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayInsertOnEmptyPath() {
        DocumentFragment<String> fragment = DocumentFragment.create(key, "", "arrayInsert");
        DocumentFragment<String> result = ctx.bucket().arrayInsertIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
