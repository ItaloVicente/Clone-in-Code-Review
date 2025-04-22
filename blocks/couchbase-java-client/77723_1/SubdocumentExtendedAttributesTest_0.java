
    @Test
    public void shouldAllowfullDocGetAndSetWithXattr() {
        String key = "XattrWithFullDoc";
        JsonObject content = JsonObject.create().put("foo", "bar");
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .upsert(content)
                .createDocument(true)
                .withExpiry(5)
                .execute(PersistTo.ONE);

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status(0));
        assertEquals(ResponseStatus.SUCCESS, result.status(1));

        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("spring.class", new SubdocOptionsBuilder().attributeAccess(true))
                .get()
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status(0));
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status(1));
        assertEquals(content, lookupResult.content(1));

    }
