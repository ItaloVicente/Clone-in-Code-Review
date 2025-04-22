    @Test
    public void shouldAddMapAsAJsonObject() {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("item1", "value1");
        map.put("item2", true);
        JsonArray arr = JsonArray.create().add(map);

        assertEquals(1, arr.size());
        assertNotNull(arr.get(0));
        assertTrue(arr.get(0) instanceof JsonObject);
        assertTrue(arr.getObject(0).containsKey("item1"));
        assertTrue(arr.getObject(0).containsKey("item2"));
    }

    @Test
    public void shouldAddListAsAJsonArray() {
        List<Object> list = new ArrayList<Object>(2);
        list.add("value1");
        list.add(true);
        JsonArray arr = JsonArray.create().add(list);

        assertEquals(1, arr.size());
        assertNotNull(arr.get(0));
        assertTrue(arr.get(0) instanceof JsonArray);
        assertEquals(2, arr.getArray(0).size());
        assertEquals("value1", arr.getArray(0).get(0));
        assertEquals(Boolean.TRUE, arr.getArray(0).get(1));
    }
