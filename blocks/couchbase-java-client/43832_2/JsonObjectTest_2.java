
    @Test
    public void shouldTreatJsonValueNullConstantAsNull() {
        JsonObject obj = JsonObject.create();
        obj.put("directNull", JsonValue.NULL);
        obj.put("subMapWithNull", JsonObject.from(
                Collections.singletonMap("subNull", JsonValue.NULL)));
        obj.put("subArrayWithNull", JsonArray.from(
                Collections.singletonList(JsonValue.NULL)));

        assertTrue(obj.containsKey("directNull"));
        assertNull(obj.get("directNull"));

        assertNotNull(obj.getObject("subMapWithNull"));
        assertTrue(obj.getObject("subMapWithNull").containsKey("subNull"));
        assertNull(obj.getObject("subMapWithNull").get("subNull"));

        assertNotNull(obj.getArray("subArrayWithNull"));
        assertNull(obj.getArray("subArrayWithNull").get(0));
    }
