    @Test
    public void testInsertEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .insert("", "foo", false); //exception thrown as soon as the builder method is invoked
    }

    @Test
    public void testUpsertEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .upsert("", "foo", false); //exception thrown as soon as the builder method is invoked
    }

    @Test
    public void testReplaceEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .replace("", "foo"); //exception thrown as soon as the builder method is invoked
    }

    @Test
    public void testRemoveEmptyPath() {
        verifyException(ctx.bucket().mutateIn(key), IllegalArgumentException.class)
                .remove(""); //exception thrown as soon as the builder method is invoked
    }

    @Test
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
                .extend("int", "something", ExtendDirection.FRONT, false)
                .doMutate();
    }

    @Test(expected = CASMismatchException.class)
    public void testExtendInBackWithBadCas() {
        ctx.bucket()
                .mutateIn(key)
                .withCas(1234L)
                .extend("int", "something", ExtendDirection.BACK, false)
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
                .extend("array", "extendFront", ExtendDirection.FRONT, false));

        assertMutationReplicated(key, timeout, persistTo, replicateTo,
                ctx.bucket().mutateIn(key)
                .extend("array", "extendBack", ExtendDirection.BACK, false));

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

    @Test
    public void testMutationWithExpiry() throws InterruptedException {
        final String expiredKey = "SubdocMutationWithExpiry";
        ctx.bucket().upsert(JsonDocument.create(expiredKey, testJson));

        Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, AsyncMutateInBuilder>() {
                    @Override
                    public AsyncMutateInBuilder call(Long tick) {
                        switch (tick.intValue()) {
                            case 0:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .addUnique("array", "foo", false);
                            case 1:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .arrayInsert("array[1]", "bar");
                            case 2:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .counter("int", 1000L, false);
                            case 3:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .extend("array", "extendFront", ExtendDirection.FRONT, false);
                            case 4:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .extend("array", "extendBack", ExtendDirection.BACK, false);
                            case 5:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .insert("sub.insert", "inserted", false);
                            case 6:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .remove("boolean");
                            case 7:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .replace("sub.value", "replaced");
                            case 8:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .upsert("newDict", JsonObject.create().put("value", 1), false);
                            case 9:
                                return ctx.bucket().async().mutateIn(expiredKey).withExpiry(1)
                                        .upsert("newDict", "notADict", false)
                                        .remove("sub");
                            default:
                                return null;
                        }
                    }
                })
                .takeWhile(new Func1<AsyncMutateInBuilder, Boolean>() {
                    @Override
                    public Boolean call(AsyncMutateInBuilder b) {
                        return b != null;
                    }
                })
                .doOnNext(new Action1<AsyncMutateInBuilder>() {
                    @Override
                    public void call(AsyncMutateInBuilder builder) {
                        LOGGER.info("Resetting expiry via {}", builder);
                    }
                })
                .flatMap(new Func1<AsyncMutateInBuilder, Observable<?>>() {
                    @Override
                    public Observable<?> call(AsyncMutateInBuilder builder) {
                        return builder.doMutate();
                    }
                })
                .toBlocking().last();

        assertNotNull("document has expired too soon", ctx.bucket().get(expiredKey));

        Thread.sleep(1500);
        assertNull("document should have expired after last operation", ctx.bucket().get(expiredKey));
    }

