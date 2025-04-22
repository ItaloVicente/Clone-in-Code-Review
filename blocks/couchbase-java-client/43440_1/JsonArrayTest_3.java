    @Test
    public void shouldConstructEmptyFromEmptyListOrNull() {
        JsonArray arr = JsonArray.from(Collections.emptyList());
        assertNotNull(arr);
        assertTrue(arr.isEmpty());

        arr = JsonArray.from((List) null);
        assertNotNull(arr);
        assertTrue(arr.isEmpty());
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
    public void shouldDetectNullItemInList() {
        try {
            JsonArray arr = JsonArray.from(Arrays.asList("item1", null, "item2"));
        } catch (NullPointerException e) {
            if (!e.getMessage().contains("1")) {
                fail("null item index not correctly present in Exception's message");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectIncorrectItemInList() {
        Object badItem = new java.lang.CloneNotSupportedException();
        JsonArray arr = JsonArray.from(Arrays.asList("item1", "item2", badItem));
    }

