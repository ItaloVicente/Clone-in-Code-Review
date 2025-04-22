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

    @Test
    public void testEntityWithNoContent() {
        EntityWithNoContent entity = new EntityWithNoContent();
        entity.id = "testEntityWithNoContent";
        EntityDocument<EntityWithNoContent> document = EntityDocument.create(entity);
        bucket.repository().upsert(document);
        EntityDocument<EntityWithNoContent> stored = bucket.repository().get(entity.id, EntityWithNoContent.class);
        Assert.assertEquals("Verifying id", stored.content().id, entity.id);
    }

