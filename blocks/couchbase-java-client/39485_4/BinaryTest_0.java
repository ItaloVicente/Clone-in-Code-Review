    @Test
    public void shouldPersistToMaster() throws Exception {
        String key = "persist-to-master";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")), PersistTo.MASTER, ReplicateTo.NONE)
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());


    }

