    @Test(expected = DurabilityException.class)
    public void shouldFailUpsertWithDurabilityException() {
        int replicaCount = bucketManager().info().replicaCount();
        Assume.assumeTrue(replicaCount < 3);

        bucket().upsert(JsonDocument.create("upsertSome", JsonObject.create()), ReplicateTo.THREE);
    }

    @Test(expected = DurabilityException.class)
    public void shouldFailInsertWithDurabilityException() {
        int replicaCount = bucketManager().info().replicaCount();
        Assume.assumeTrue(replicaCount < 3);

        bucket().insert(JsonDocument.create("insertSome", JsonObject.create()), ReplicateTo.THREE);
    }

    @Test(expected = DurabilityException.class)
    public void shouldFailReplaceWithDurabilityException() {
        int replicaCount = bucketManager().info().replicaCount();
        Assume.assumeTrue(replicaCount < 3);

        bucket().upsert(JsonDocument.create("replaceSome", JsonObject.create()));
        bucket().replace(JsonDocument.create("replaceSome", JsonObject.create()), ReplicateTo.THREE);
    }

    @Test(expected = DurabilityException.class)
    public void shouldFailRemoveWithDurabilityException() {
        int replicaCount = bucketManager().info().replicaCount();
        Assume.assumeTrue(replicaCount < 3);

        bucket().upsert(JsonDocument.create("removeSome", JsonObject.create()));
        bucket().replace(JsonDocument.create("removeSome", JsonObject.create()), ReplicateTo.THREE);
    }

