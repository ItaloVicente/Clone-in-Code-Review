    @Test
    public void testEntityWithNoEncryption() {
        EntityWithNoEncryption entity = new EntityWithNoEncryption();
        entity.id = "testEntityWithNoEncryption";
        entity.content = "foobar";
        EntityDocument<EntityWithNoEncryption> document = EntityDocument.create(entity);
        bucket.repository().upsert(document);
        EntityDocument<EntityWithNoEncryption> stored = bucket.repository().get(entity.id, EntityWithNoEncryption.class);
        Assert.assertEquals("Verifying content", entity.content, stored.content().content);
    }

