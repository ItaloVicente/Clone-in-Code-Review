    @Test
    public void shouldConstructEmptyArrayFromEmptyList() {
        JsonArray arr = JsonArray.from(Collections.emptyList());
        assertNotNull(arr);
        assertTrue(arr.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNullPointerOnNullList() {
        JsonArray.from((List) null);
    }

    @Test
    public void shouldConstructArrayFromList() {
        String item1 = "item1";
        Double item2 = 2.0d;
        Long item3 = 3L;
        Boolean item4 = true;
        JsonArray item5 = JsonArray.empty();
        JsonObject item6 = JsonObject.empty();

        JsonArray arr = JsonArray.from(Arrays.asList(item1, item2,
                item3, item4, item5, item6));

        assertEquals(6, arr.size());
        assertEquals(item1, arr.get(0));
        assertEquals(item2, arr.get(1));
        assertEquals(item3, arr.get(2));
        assertEquals(item4, arr.get(3));
        assertEquals(item5, arr.get(4));
        assertEquals(item6, arr.get(5));
    }

    @Test
    public void shouldAcceptNullItemInList() {
        JsonArray arr = JsonArray.from(Arrays.asList("item1", null, "item2"));
        assertNotNull(arr);
        assertEquals(3, arr.size());
        assertNotNull(arr.get(0));
        assertNotNull(arr.get(2));
        assertNull(arr.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectIncorrectItemInList() {
        Object badItem = new java.lang.CloneNotSupportedException();
        JsonArray arr = JsonArray.from(Arrays.asList("item1", "item2", badItem));
    }

    @Test
    public void shouldRecursiveParseList() {
        List<?> subList = Collections.singletonList("test");
        List<Object> source = new ArrayList<Object>(2);
        source.add("item1");
        source.add(subList);

        JsonArray arr = JsonArray.from(source);
        assertNotNull(arr);
        assertEquals(2, arr.size());
        assertEquals("item1", arr.getString(0));
        assertEquals(JsonArray.class, arr.get(1).getClass());
        assertEquals("test", arr.getArray(1).get(0));
    }

    @Test
    public void shouldRecursiveParseMap() {
        Map<String, ?> subMap = Collections.singletonMap("test", 2.5d);
        List<Object> source = new ArrayList<Object>(2);
        source.add("item1");
        source.add(subMap);

        JsonArray arr = JsonArray.from(source);
        assertNotNull(arr);
        assertEquals(2, arr.size());
        assertEquals("item1", arr.getString(0));
        assertEquals(JsonObject.class, arr.get(1).getClass());
        assertEquals(new Double(2.5d), arr.getObject(1).get("test"));
    }

    @Test
    public void shouldClassCastOnBadSubMap() {
        Map<Integer, String> badMap1 = Collections.singletonMap(1, "test");
        Map<String, Object> badMap2 = Collections.singletonMap("key1", (Object) new CloneNotSupportedException());

        List<?> source = Collections.singletonList(badMap1);
        try {
            JsonArray.from(source);
            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (e.getCause() != null) {
                fail("No cause expected for sub map that are not Map<String, ?>");
            }
        } catch (Exception e) {
            fail("ClassCastException expected, not " + e);
        }

        source = Collections.singletonList(badMap2);
        try {
            JsonArray.from(source);
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
        List<?> source = Collections.singletonList(badSubList);
        try {
            JsonArray.from(source);
            fail("ClassCastException expected");
        } catch (ClassCastException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("ClassCastException with an IllegalArgumentException cause expected");
            }
        } catch (Exception e) {
            fail("ClassCastException expected");
        }
    }

