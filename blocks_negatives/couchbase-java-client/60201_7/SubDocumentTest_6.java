        DocumentFragment<String> fragment = DocumentFragment.create(key, "integer", null);
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);

        assertNotNull(result);
        assertNull(result.fragment());
        assertNotEquals(fragment.cas(), result.cas());
        assertFalse(ctx.bucket().get(key).content().containsKey("int"));
