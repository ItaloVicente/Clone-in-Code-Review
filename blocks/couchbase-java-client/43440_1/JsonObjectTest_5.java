    @Test
    public void shouldConstructEmptyFromEmptyMapOrNull() {
        JsonObject obj = JsonObject.from(Collections.<String, Object>emptyMap());
        assertNotNull(obj);
        assertTrue(obj.isEmpty());

        obj = JsonObject.from(null);
        assertNotNull(obj);
        assertTrue(obj.isEmpty());
    }

    @Test
    public void shouldConstructJsonObjectFromMap() {
        String item1 = "item1";
        Double item2 = 2.2d;
        Long item3 = 3L;
        Boolean item4 = true;
        JsonArray item5 = JsonArray.empty();
        JsonObject item6 = JsonObject.empty();
        Map<String, Object> source = new HashMap<String, Object>(6);
        source.put("key1", item1);
        source.put("key2", item2);
        source.put("key3", item3);
        source.put("key4", item4);
        source.put("key5", item5);
        source.put("key6", item6);

        JsonObject obj = JsonObject.from(source);
        assertNotNull(obj);
        assertEquals(6, obj.size());
        assertEquals(item1, obj.get("key1"));
        assertEquals(item2, obj.get("key2"));
        assertEquals(item3, obj.get("key3"));
        assertEquals(item4, obj.get("key4"));
        assertEquals(item5, obj.get("key5"));
        assertEquals(item6, obj.get("key6"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldDetectNullKeyInMap() {
        Map<String, Double> badMap = new HashMap<String, Double>(2);
        badMap.put("key1", 1.1d);
        badMap.put(null, 2.2d);
        JsonObject.from(badMap);
    }

    @Test
    public void shouldDetectNullValueInMap() {
        Map<String, Long> badMap = new HashMap<String, Long>(2);
        badMap.put("key1", 1L);
        badMap.put("key2", null);

        try {
            JsonObject obj = JsonObject.from(badMap);
        } catch (NullPointerException e) {
            if (!e.getMessage().contains("key2")) {
                fail("Null value should output incriminating key");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectIncorrectItemInMap() {
        Object badItem = new java.lang.CloneNotSupportedException();
        Map<String, Object> badMap = new HashMap<String, Object>(1);
        badMap.put("key1", badItem);
        JsonObject.from(badMap);
    }

