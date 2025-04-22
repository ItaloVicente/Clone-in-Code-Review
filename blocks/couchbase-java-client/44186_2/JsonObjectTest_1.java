
    @Test
    public void shouldPutMapAsAJsonObject() {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("item1", "value1");
        map.put("item2", true);
        JsonObject obj = JsonObject.create().put("sub", map);

        assertTrue(obj.containsKey("sub"));
        assertNotNull(obj.get("sub"));
        assertTrue(obj.get("sub") instanceof JsonObject);
        assertTrue(obj.getObject("sub").containsKey("item1"));
        assertTrue(obj.getObject("sub").containsKey("item2"));
    }

    @Test
    public void shouldPutListAsAJsonArray() {
        List<Object> list = new ArrayList<Object>(2);
        list.add("value1");
        list.add(true);
        JsonObject obj = JsonObject.create().put("sub", list);

        assertTrue(obj.containsKey("sub"));
        assertNotNull(obj.get("sub"));
        assertTrue(obj.get("sub") instanceof JsonArray);
        assertEquals(2, obj.getArray("sub").size());
        assertEquals("value1", obj.getArray("sub").get(0));
        assertEquals(Boolean.TRUE, obj.getArray("sub").get(1));
    }
