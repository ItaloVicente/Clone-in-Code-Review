    @Test
    public void shouldUpsertExtendedEntity() {
        Child entity = new Child("myid", "myname");
        EntityDocument<Child> document = EntityDocument.create(entity);

        assertFalse(repository().exists(document));
        assertEquals(0, document.cas());
        EntityDocument<Child> stored = repository().upsert(document);
        assertNotEquals(0, stored.cas());
        assertEquals(document.content(), stored.content());

        JsonDocument storedRaw = bucket().get(entity.getId());
        assertEquals(entity.getName(), storedRaw.content().getString("name"));

        EntityDocument<Child> found = repository().get(entity.getId(), Child.class);
        assertEquals(found.cas(), stored.cas());
        assertNotEquals(0, found.cas());

        assertTrue(repository().exists(document));
    }

