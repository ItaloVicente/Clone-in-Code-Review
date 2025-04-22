
    @Test
    public void shouldAllowfullDocInsertWithXattr() {
        String key = "shouldAllowfullDocInsertWithXattr";
        JsonObject content = JsonObject.create().put("foo", "bar");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(false).xattr(true))
                .upsert(content)
                .insertDocument(true)
                .execute(PersistTo.ONE);
        assertEquals(ResponseStatus.SUCCESS, result.status(0));
    }

    @Test(expected = CASMismatchException.class)
    public void shouldFailfullDocInsertWithXattrOnExistingDoc() {
        String key = "shouldFailfullDocInsertWithXattrOnExistingDoc";
        JsonObject content = JsonObject.create().put("foo", "bar");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(false).xattr(true))
                .upsert(content)
                .insertDocument(true)
                .execute(PersistTo.ONE);
        assertEquals(ResponseStatus.SUCCESS, result.status(0));
        DocumentFragment<Mutation> upsertResult2 = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass2", new SubdocOptionsBuilder().createParents(false).xattr(true))
                .insertDocument(true)
                .execute(PersistTo.ONE);
    }

    @Test
    public void shouldAllowDeletedDocumentXattrLookup() {
        String key = "shouldAllowDeletedDocumentXattrLookup";
        JsonObject content = JsonObject.create().put("foo", "bar");
        DocumentFragment<Mutation> mutationResult = ctx.bucket()
                .mutateIn(key)
                .upsert("_class", "SomeClass", new SubdocOptionsBuilder().createParents(false).xattr(true))
                .upsert(content)
                .insertDocument(true)
                .execute(PersistTo.ONE);
        assertEquals(ResponseStatus.SUCCESS, mutationResult.status(0));
        ctx.bucket().remove(key);
        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("_class", new SubdocOptionsBuilder().xattr(true))
                .accessDeleted(true)
                .execute();
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status(0));
        assertEquals(lookupResult.content(0), "SomeClass");
    }
