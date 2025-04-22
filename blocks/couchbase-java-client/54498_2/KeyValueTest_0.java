    @Test(expected = CASMismatchException.class)
    public void shouldFailWithInvalidCASOnAppend() {
        StringDocument stored = bucket().upsert(StringDocument.create("appendCasMismatch", "foo"));
        bucket().append(StringDocument.from(stored, stored.cas() + 1));
    }

    @Test(expected = CASMismatchException.class)
    public void shouldFailWithInvalidCASOnPrepend() {
        StringDocument stored = bucket().upsert(StringDocument.create("prependCasMismatch", "foo"));
        bucket().prepend(StringDocument.from(stored, stored.cas() + 1));
    }

