    public void testArrayInsertEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .arrayInsert("", "foo"); //exception thrown as soon as the builder method is invoked
    }

    @Test
    public void testCounterEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .counter("", 1000L, false); //exception thrown as soon as the builder method is invoked
    }

    @Test(expected = CASMismatchException.class)
    public void testUpsertInWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .upsert("int", null, false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testInsertInWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .insert("int", null, false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testReplaceInWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .replace( "int", null)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testExtendInFrontWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .pushFront("int", "something", false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testExtendInBackWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .pushBack("int", "something", false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testArrayInsertWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .arrayInsert("int", null)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testArrayAddUniqueWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .addUnique("int", null, false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testRemoveWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .remove("int")
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testCounterInWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .counter("int", 1000L, false)
                .doMutate();
    }

    @Test
    public void testSingleMutationWithDurability() {
        int numReplicas = ctx.bucketManager().info().replicaCount();
        int numNodes = ctx.bucketManager().info().nodeCount();
        Assume.assumeTrue("At least one available replica is necessary for this test", numReplicas >= 1 && numNodes >= (numReplicas + 1));

        int timeout = 10;
        PersistTo persistTo = PersistTo.MASTER;
        ReplicateTo replicateTo = ReplicateTo.ONE;

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                        .addUnique("array", "foo", false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                        .arrayInsert("array[1]", "bar"));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                        .counter("int", 1000L, false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .pushFront("array", "extendFront", false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .pushBack("array", "extendBack", false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .insert("sub.insert", "inserted", false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .remove("boolean"));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .replace("sub.value", "replaced"));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .upsert("newDict", JsonObject.create().put("value", 1), false));

        JsonObject expected = JsonObject.create()
                .put("sub", JsonObject.create().put("value", "replaced").put("insert", "inserted"))
                .put("newDict", JsonObject.create().put("value", 1))
                .put("string", "someString")
                .put("int", 1123)
                .put("array", JsonArray.from("extendFront", "1", "bar", 2, true, "foo", "extendBack"));
        assertEquals(expected, ctx.bucket().get(key).content());
    }

    @Test
    public void testMultiMutationWithDurability() {
        int numReplicas = ctx.bucketManager().info().replicaCount();
        int numNodes = ctx.bucketManager().info().nodeCount();
        Assume.assumeTrue("At least one available replica is necessary for this test", numReplicas >= 1 && numNodes >= (numReplicas + 1));

        int timeout = 10;
        PersistTo persistTo = PersistTo.MASTER;
        ReplicateTo replicateTo = ReplicateTo.ONE;

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket()
                .mutateIn(key)
                .addUnique("array", "foo", false)
                .remove("boolean"));

        JsonObject expected = JsonObject.create()
                .put("sub", JsonObject.create().put("value", "original"))
                .put("string", "someString")
                .put("int", 123)
                .put("array", JsonArray.from("1", 2, true, "foo"));
        assertEquals(expected, ctx.bucket().get(key).content());
    }

    private void assertMutationReplicated(String key, int timeout, PersistTo persistTo, ReplicateTo replicateTo,
            MutateInBuilder mutateInBuilder) {
        mutateInBuilder.withDurability(persistTo, replicateTo);
        LOGGER.info("Asserting replication of {}", mutateInBuilder);

        DocumentFragment<Mutation> result = mutateInBuilder.doMutate(timeout, TimeUnit.SECONDS);

        JsonDocument masterDoc = ctx.bucket().get(key);
        JsonDocument replicaDoc = ctx.bucket().getFromReplica(key, ReplicaMode.FIRST).get(0);

        assertNotNull("result is null", result);
        assertEquals("master doc and fragment cas differ", masterDoc.cas(), result.cas());
        assertEquals("master doc and fragment mutation token differ", masterDoc.mutationToken(), result.mutationToken());

        assertEquals("replicated doc and fragment cas differ", replicaDoc.cas(), result.cas());
        assertEquals("replicated doc and fragment token differ", replicaDoc.mutationToken(), result.mutationToken());
        assertEquals("master doc and replicated doc contents differ", masterDoc.content(), replicaDoc.content());
    }

    private void assertMutationWithExpiry(String expiredKey, MutateInBuilder builder, int expirySeconds) throws InterruptedException {
        ctx.bucket().upsert(JsonDocument.create(expiredKey, testJson), PersistTo.MASTER);

        builder = builder.withExpiry(expirySeconds);
        LOGGER.info("Resetting expiry via {}", builder);
        DocumentFragment<Mutation> result = builder.doMutate();

        assertNotNull("mutation failed", result);
        assertNotNull("document has expired too soon", ctx.bucket().get(expiredKey));

        Thread.sleep(expirySeconds * 1000 * 2);
        assertNull("document should have expired after last operation", ctx.bucket().get(expiredKey));
    }

    @Test
    public void testMutationWithExpirySingleCommonPath() throws InterruptedException {
        final String expiredKey = "SubdocMutationWithExpirySingleCommonPath";
        MutateInBuilder builder = ctx.bucket()
                .mutateIn(expiredKey)
                .addUnique("array", "foo", false);

        assertMutationWithExpiry(expiredKey, builder, 3);
    }

    @Test
    public void testMutationWithExpirySingleRemovePath() throws InterruptedException {
        final String expiredKey = "SubdocMutationWithExpirySingleRemovePath";
        MutateInBuilder builder = ctx.bucket()
                .mutateIn(expiredKey)
                .remove("boolean");

        assertMutationWithExpiry(expiredKey, builder, 1);
    }

    @Test
    public void testMutationWithExpirySingleCounterPath() throws InterruptedException {
        final String expiredKey = "SubdocMutationWithExpirySingleCounterPath";
        MutateInBuilder builder = ctx.bucket()
                .mutateIn(expiredKey)
                .counter("int", 1000L, false);

        assertMutationWithExpiry(expiredKey, builder, 1);
    }

    @Test
    public void testMutationWithExpiryMultiPath() throws InterruptedException {
        final String expiredKey = "SubdocMutationWithExpiryMultiPath";
        MutateInBuilder builder = ctx.bucket()
                .mutateIn(expiredKey)
                .upsert("newDict", "notADict", false)
                .remove("sub");

        assertMutationWithExpiry(expiredKey, builder, 1);
