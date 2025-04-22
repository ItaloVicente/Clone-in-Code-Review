    @Test
    public void shouldBeAbleToPersistXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .execute(PersistTo.ONE);

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.class"));

        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("spring.class", new SubdocOptionsBuilder().attributeAccess(true))
                .execute();
        assertTrue(lookupResult.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status("spring.class"));
    }

    @Test
    public void shouldNotBeAbleToGetXATTRWithoutAccessFlagSet() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .execute(PersistTo.ONE);

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.class"));

        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("spring.class", new SubdocOptionsBuilder().attributeAccess(false))
                .execute();
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, lookupResult.status("spring.class"));
    }

    @Test
    public void verifyExistXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.class", "SomeClass", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .execute(PersistTo.ONE);

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.class"));

        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .exists("spring.class", new SubdocOptionsBuilder().attributeAccess(true))
                .execute();
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status("spring.class"));
    }


    @Test
    public void verifyArrayOpsXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayAddUnique("spring.refs", "id1", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .execute();

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));


        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .exists("spring.refs", new SubdocOptionsBuilder().attributeAccess(true))
                .execute();
        assertEquals(ResponseStatus.SUCCESS, lookupResult.status("spring.refs"));

        result = ctx.bucket()
                .mutateIn(key)
                .arrayAppend("spring.refs", "id0", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));

        result = ctx.bucket()
                .mutateIn(key)
                .arrayPrepend("spring.refs", "id2", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));

        result = ctx.bucket()
                .mutateIn(key)
                .arrayInsert("spring.refs[0]", "id3", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs[0]"));

        result = ctx.bucket()
                .mutateIn(key)
                .remove("spring.refs[0]", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs[0]"));

        result = ctx.bucket()
                .mutateIn(key)
                .remove("spring.refs", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));
    }

    @Test
    public void verifyChainArrayOpsXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayAddUnique("spring.refs", "id1", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .arrayAppend("spring.refs", "id0", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .arrayPrepend("spring.refs", "id2", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .arrayInsert("spring.refs[0]", "id3", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .remove("spring.refs[0]", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .remove("spring.refs", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs[0]"));
    }

    @Test
    public void verifyChainArrayCollectionOpsXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayAppendAll("spring.refs", Arrays.asList("id4", "id5", "id6", "id7"), new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .arrayPrependAll("spring.refs", Arrays.asList("id0", "id1", "id2", "id3"), new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .arrayInsertAll("spring.refs[0]",  Arrays.asList("id8", "id9", "id10", "id11"), new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));

        DocumentFragment<Lookup> lookupResult = ctx.bucket()
                .lookupIn(key)
                .get("spring.refs",new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertEquals(12, ((JsonArray)lookupResult.content("spring.refs")).size());


        result = ctx.bucket()
                .mutateIn(key)
                .remove("spring.refs", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.refs"));
    }

    @Test
    public void verifyDictOpsXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("spring.dict.foo1", "bar1", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .execute();

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo1"));

        result = ctx.bucket()
                .mutateIn(key)
                .insert("spring.dict.foo2", "bar2", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo2"));

        result = ctx.bucket()
                .mutateIn(key)
                .upsert("spring.dict.foo1", 0, new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo1"));

        result = ctx.bucket()
                .mutateIn(key)
                .counter("spring.dict.foo1", 1, new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo1"));


        result = ctx.bucket()
                .mutateIn(key)
                .remove("spring.dict", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict"));
    }


    @Test
    public void verifyChainDictOpsXATTR() {
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("spring.dict.foo1", "bar1", new SubdocOptionsBuilder().createParents(true).attributeAccess(true))
                .insert("spring.dict.foo2", "bar2", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .upsert("spring.dict.foo1", 0, new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .counter("spring.dict.foo1", 1, new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo1"));
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict.foo2"));


        result = ctx.bucket()
                .mutateIn(key)
                .remove("spring.dict", new SubdocOptionsBuilder().createParents(false).attributeAccess(true))
                .execute();
        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("spring.dict"));
    }


