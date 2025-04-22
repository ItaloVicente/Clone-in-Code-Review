        DocumentFragment<String> fragment = DocumentFragment.create(key, "sub.valuezz", null);
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }
    
    @Test(expected = PathNotFoundException.class)
    public void testRemoveArrayElementWithIndexOutOfBounds() {
        DocumentFragment<String> fragment = DocumentFragment.create(key, "array[4]", null);
        DocumentFragment<String> result = ctx.bucket().removeIn(fragment, PersistTo.NONE, ReplicateTo.NONE);
    }
