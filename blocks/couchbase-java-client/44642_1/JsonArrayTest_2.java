    @Test
    public void shouldConvertSubJsonValuesToCollections() {
        JsonObject sub1 = JsonObject.create().put("sub1.1", "test");
        JsonArray sub2 = JsonArray.create().add("sub2.1");
        JsonArray arr = JsonArray.create()
                                   .add(sub1)
                                   .add(sub2);

        List<Object> asList = arr.toList();
        Object mSub1 = asList.get(0);
        Object mSub2 = asList.get(1);

        assertNotNull(mSub1);
        assertTrue(mSub1 instanceof Map);
        assertEquals("test", ((Map) mSub1).get("sub1.1"));

        assertNotNull(mSub2);
        assertTrue(mSub2 instanceof List);
        assertEquals("sub2.1", ((List) mSub2).get(0));
    }

    @Test
    public void shouldThrowIfAddingToSelf() {
        JsonArray arr = JsonArray.create();
        try {
            arr.add(arr);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            arr.add((Object) arr);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

