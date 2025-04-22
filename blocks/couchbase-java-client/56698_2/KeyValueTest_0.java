    @Test(expected = CASMismatchException.class)
    public void shouldHonorCASOnRemoveWithDurability() {
        JsonDocument toStore = JsonDocument.create("casWithDurability", JsonObject.create().put("initial", true));
        assertTrue(toStore.cas() == 0);

        JsonDocument stored = bucket().insert(toStore, PersistTo.MASTER);
        assertTrue(stored.cas() != 0);

        bucket().remove(JsonDocument.from(stored, stored.cas() + 1), PersistTo.MASTER);
    }

