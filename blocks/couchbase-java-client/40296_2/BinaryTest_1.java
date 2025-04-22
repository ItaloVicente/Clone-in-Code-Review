        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")),
            PersistTo.MASTER, ReplicateTo.NONE).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());
    }

    @Test
    public void shouldRemoveFromMaster() {
        String key = "remove-from-master";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")),
            PersistTo.MASTER, ReplicateTo.NONE).toBlocking().single();
