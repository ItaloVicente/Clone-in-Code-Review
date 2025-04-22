    @Test
    public void testUseWithNoEncryptedFields() {
        JsonDocument document = JsonDocument.create("testUseWithNoEncryptedFields",
                JsonObject.create().put("foo", "bar"));
        bucket.upsert(document);
        JsonDocument stored = bucket.get(document);
        Assert.assertEquals("Verifying content", "bar", stored.content().get("foo"));
    }

    @Test
    public void testUseWithNull() {
        JsonDocument document = JsonDocument.create("testUseWithNull", null);
        bucket.upsert(document);
        JsonDocument stored = bucket.get(document);
        Assert.assertEquals("Verifying content", null, stored.content());
    }
