
    @Test
    public void shouldTreatJsonValueNullConstantAsNull() {
        JsonArray arr = JsonArray.create();
        arr.add(JsonValue.NULL);
        arr.add(JsonObject.from(
                Collections.singletonMap("subNull", JsonValue.NULL)));
        arr.add(JsonArray.from(
                Collections.singletonList(JsonValue.NULL)));

        assertEquals(3, arr.size());
        assertNull(arr.get(0));
        assertNotNull(arr.getObject(1));
        assertTrue(arr.getObject(1).containsKey("subNull"));
        assertNull(arr.getObject(1).get("subNull"));

        assertNotNull(arr.getArray(2));
        assertNull(arr.getArray(2).get(0));
    }

