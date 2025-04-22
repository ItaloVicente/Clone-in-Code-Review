    @Test
    public void shouldAppendString() {
        String id ="append1";
        String value = "foo";

        LegacyDocument doc = LegacyDocument.create(id, value);
        LegacyDocument stored = bucket().upsert(doc).toBlocking().single();
        assertTrue(stored.status().isSuccess());

        stored = bucket().append(LegacyDocument.create(id, "bar")).toBlocking().single();
        assertTrue(stored.status().isSuccess());

        LegacyDocument found = bucket().get(id, LegacyDocument.class).toBlocking().single();
        assertEquals("foobar", found.content());
    }

    @Test
    public void shouldPrependString() {
        String id ="prepend1";
        String value = "bar";

        LegacyDocument doc = LegacyDocument.create(id, value);
        LegacyDocument stored = bucket().upsert(doc).toBlocking().single();
        assertTrue(stored.status().isSuccess());

        stored = bucket().prepend(LegacyDocument.create(id, "foo")).toBlocking().single();
        assertTrue(stored.status().isSuccess());

        LegacyDocument found = bucket().get(id, LegacyDocument.class).toBlocking().single();
        assertEquals("foobar", found.content());
    }

    @Test(expected = DocumentDoesNotExistException.class)
    public void shouldFailOnNonExistingAppend() {
        LegacyDocument doc = LegacyDocument.create("appendfail", "fail");
        bucket().append(doc).toBlocking().single();
    }

