    @Test(expected = DocumentDoesNotExistException.class)
    public void shouldFailOnNonExistingPrepend() {
        LegacyDocument doc = LegacyDocument.create("prependfail", "fail");
        bucket().prepend(doc);
    }

