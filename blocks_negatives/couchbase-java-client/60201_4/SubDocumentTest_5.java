    @Test
    public void testRemoveIgnoreFragment() {
        DocumentFragment<String> fragment = DocumentFragment.create(key, "int", "anyFragmentGoesThere");
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);

        assertNotNull(result);
        assertNull(result.fragment());
        assertFalse(ctx.bucket().get(key).content().toString().contains("anyFragmentGoesThere"));
    }

