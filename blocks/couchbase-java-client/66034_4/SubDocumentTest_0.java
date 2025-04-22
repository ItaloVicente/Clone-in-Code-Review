
    @Test(expected = DurabilityException.class)
    public void shouldFailMutationWithDurabilityException() {
        int replicaCount = ctx.bucketManager().info().replicaCount();
        Assume.assumeTrue(replicaCount < 3);

        ctx.bucket()
            .mutateIn(key)
            .upsert("sub", 1234, false)
            .execute(PersistTo.FOUR);
    }

    @Test
    public void shouldPersistMutation() {
        DocumentFragment<Mutation> result = ctx.bucket()
            .mutateIn(key)
            .upsert("string", "iPersist", false)
            .execute(PersistTo.ONE);

        assertTrue(result.cas() != 0);
        assertEquals(ResponseStatus.SUCCESS, result.status("string"));
    }

