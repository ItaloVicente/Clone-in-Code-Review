    @Test(expected = NoSuchElementException.class)
    public void shouldGetNonexistentAndFail() {
        bucket().get("i-dont-exist").toBlocking().single();
    }

    @Test
    public void shouldGetNonexistentWithDefault() {
        JsonDocument jsonDocument = bucket().get("i-dont-exist").toBlocking().singleOrDefault(null);
        assertNull(jsonDocument);
    }

    @Test(expected = DocumentAlreadyExistsException.class)
    public void shouldErrorOnDoubleInsert() {
        String id = "double-insert";
        JsonObject content = JsonObject.empty().put("hello", "world");
        final JsonDocument doc = JsonDocument.create(id, content);
        bucket().insert(doc).toBlocking().single();
        bucket().insert(doc).toBlocking().single();
    }

    @Test
    public void shouldInsertAndGet() {
        JsonObject content = JsonObject.empty().put("hello", "world");
        final JsonDocument doc = JsonDocument.create("insert", content);
        JsonDocument response = bucket()
            .insert(doc)
            .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
                @Override
                public Observable<JsonDocument> call(JsonDocument document) {
                    return bucket().get("insert");
                }
            })
            .toBlocking()
            .single();
        assertEquals(content.getString("hello"), response.content().getString("hello"));
    }
