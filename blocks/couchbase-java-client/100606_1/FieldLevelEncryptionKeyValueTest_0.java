    @Test
    public void testUseWithNoEncryptedFields() throws Exception {
        JsonDocument document = JsonDocument.create("testUseWithNoEncryptedFields",
                JsonObject.create().put("foo", "bar"));
        bucket.upsert(document);
        JsonDocument stored = bucket.get(document);
        Assert.assertEquals("bar", stored.content().get("foo"));
    }

    @Test
    public void testUseWithNull() throws Exception {
        JsonDocument document = JsonDocument.create("testUseWithNull", null);
        bucket.upsert(document);
        JsonDocument stored = bucket.get(document);
        Assert.assertEquals(null, stored.content());
    }
