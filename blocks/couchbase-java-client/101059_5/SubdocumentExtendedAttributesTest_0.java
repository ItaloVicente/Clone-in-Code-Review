
    @Test
    public void shouldExpandMacro() {
        String key = "shouldExpandMacro";
        JsonObject content = JsonObject.create().put("foo", "bar");
        DocumentFragment<Mutation> mutationResult = ctx.bucket()
                .mutateIn(key)
                .upsert("insertedCas", "${Mutation.CAS}", new SubdocOptionsBuilder()
                        .xattr(true).expandMacros(true))
                .upsert(content)
                .insertDocument(true)
                .execute(PersistTo.ONE);
        assertEquals(ResponseStatus.SUCCESS, mutationResult.status(0));
        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("insertedCas", new SubdocOptionsBuilder().xattr(true))
                .execute();
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status(0));
        assertTrue(((String) lookupResult.content(0)).startsWith("0x0000"));
    }

