    @Test
    public void shouldUpsertMetaEntity() {
        MetaEntity entity = new MetaEntity("myid", "myname");
        EntityDocument<MetaEntity> document = EntityDocument.create(entity);

        assertFalse(repository().exists(document));
        assertEquals(0, document.cas());
        EntityDocument<MetaEntity> stored = repository().upsert(document);
        assertNotEquals(0, stored.cas());
        assertEquals(document.content(), stored.content());

        JsonDocument storedRaw = bucket().get(entity.getId());
        assertEquals(entity.getName(), storedRaw.content().getString("name"));

        EntityDocument<MetaEntity> found = repository().get(entity.getId(), MetaEntity.class);
        assertEquals(found.cas(), stored.cas());
        assertNotEquals(0, found.cas());

        assertTrue(repository().exists(document));
    }

