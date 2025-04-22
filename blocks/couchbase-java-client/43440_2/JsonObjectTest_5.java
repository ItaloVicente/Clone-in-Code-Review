    @Test
    public void shouldConstructEmptyObjectFromEmptyMap() {
        JsonObject obj = JsonObject.from(Collections.<String, Object>emptyMap());
        assertNotNull(obj);
        assertTrue(obj.isEmpty());
    }


    @Test(expected = NullPointerException.class)
    public void shouldNullPointerOnNullMap() {
        JsonObject.from(null);
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
    public void shouldAcceptNullValueInMap() {
        Map<String, Long> badMap = new HashMap<String, Long>(2);
        badMap.put("key1", 1L);
        badMap.put("key2", null);

        JsonObject obj = JsonObject.from(badMap);
        assertNotNull(obj);
        assertEquals(2, obj.size());
        assertNotNull(obj.get("key1"));
        assertNull(obj.get("key2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectIncorrectItemInMap() {
        Object badItem = new CloneNotSupportedException();
        Map<String, Object> badMap = new HashMap<String, Object>(1);
        badMap.put("key1", badItem);
        JsonObject.from(badMap);
    }

    @Test
    public void shouldRecursivelyParseMaps() {
        Map<String, Double> subMap = new HashMap<String, Double>(2);
        subMap.put("value1", 1.2d);
        subMap.put("value2", 3.4d);
        Map<String, Object> recurseMap = new HashMap<String, Object>(2);
        recurseMap.put("key1", "test");
        recurseMap.put("key2", subMap);

        JsonObject obj = JsonObject.from(recurseMap);
        assertNotNull(obj);
        assertEquals(2, obj.size());
        assertEquals("test", obj.getString("key1"));

        assertNotNull(obj.get("key2"));
        assertEquals(JsonObject.class, obj.get("key2").getClass());
        assertEquals(2, obj.getObject("key2").size());
    }

    @Test
    public void shouldRecursivelyParseLists() {
        List<Double> subList = new ArrayList<Double>(2);
        subList.add(1.2d);
        subList.add(3.4d);
        Map<String, Object> recurseMap = new HashMap<String, Object>(2);
        recurseMap.put("key1", "test");
        recurseMap.put("key2", subList);

        JsonObject obj = JsonObject.from(recurseMap);
        assertNotNull(obj);
        assertEquals(2, obj.size());
        assertEquals("test", obj.getString("key1"));

        assertNotNull(obj.get("key2"));
        assertEquals(JsonArray.class, obj.get("key2").getClass());
        assertEquals(2, obj.getArray("key2").size());
    }

    @Test
    public void shouldClassCastOnBadSubMap() {
        Map<Integer, String> badMap1 = Collections.singletonMap(1, "test");
        Map<String, Object> badMap2 = Collections.singletonMap("key1", (Object) new CloneNotSupportedException());

        Map<String, Object> sourceMap = new HashMap<String, Object>();
        sourceMap.put("subMap", badMap1);
        try {
            JsonObject.from(sourceMap);
            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (e.getCause() != null) {
                fail("No cause expected for sub map that are not Map<String, ?>");
            }
        } catch (Exception e) {
            fail("ClassCastException expected, not " + e);
        }

        sourceMap.clear();
        sourceMap.put("subMap", badMap2);
        try {
            JsonObject.from(sourceMap);
            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("ClassCastException with an IllegalArgumentException cause expected");
            }
        } catch (Exception e) {
            fail("ClassCastException expected");
        }
    }

    @Test
    public void shouldClassCastWithCauseOnBadSubList() {
        List<?> badSubList = Collections.singletonList(new CloneNotSupportedException());
        Map<String, ?> source = Collections.singletonMap("test", badSubList);
        try {
            JsonObject.from(source);
            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("ClassCastException with an IllegalArgumentException cause expected");
            }
        } catch (Exception e) {
            fail("ClassCastException expected");
        }
    }
