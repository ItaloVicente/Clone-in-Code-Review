
    @Test
    public void shouldConvertSubJsonValuesToCollections() {
        JsonObject sub1 = JsonObject.create().put("sub1.1", "test");
        JsonArray sub2 = JsonArray.create().add("sub2.1");
        JsonObject obj = JsonObject.create()
                .put("sub1", sub1)
                .put("sub2", sub2);

        Map<String, Object> asMap = obj.toMap();
        Object mSub1 = asMap.get("sub1");
        Object mSub2 = asMap.get("sub2");

        assertNotNull(mSub1);
        assertTrue(mSub1 instanceof Map);
        assertEquals("test", ((Map) mSub1).get("sub1.1"));

        assertNotNull(mSub2);
        assertTrue(mSub2 instanceof List);
        assertEquals("sub2.1", ((List) mSub2).get(0));
    }

    @Test
    public void shouldThrowIfAddingToSelf() {
        JsonObject obj = JsonObject.create();
        try {
            obj.put("test", obj);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            obj.put("test", (Object) obj);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
