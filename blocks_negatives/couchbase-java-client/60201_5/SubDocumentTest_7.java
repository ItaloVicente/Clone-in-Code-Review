        DocumentFragment<String> fragment = DocumentFragment.create(key, "array[4]", null);
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEmptyPath() {
        DocumentFragment<String> fragment = DocumentFragment.create(key, "", null);
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }

    @Test(expected = CASMismatchException.class)
    public void testRemoveWithBadCas() {
        DocumentFragment<Long> fragment = DocumentFragment.create(key, "int", null, 1234L);
        ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
