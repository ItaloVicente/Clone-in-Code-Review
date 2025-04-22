
    @Test(expected = XattrOrderingException.class)
    public void shouldFailIfXattrLookupIsNotFirst() {
        new AsyncLookupInBuilder(null, null, null, null, "id")
            .get("foo")
            .get("bar", new SubdocOptionsBuilder().xattr(true))
            .execute();
    }

    @Test(expected = XattrOrderingException.class)
    public void shouldFailIfXattrMutateIsNotFirst() {
        new AsyncMutateInBuilder(null, null, null, null, "id")
            .remove("foo")
            .remove("bar", new SubdocOptionsBuilder().xattr(true))
            .execute();
    }
