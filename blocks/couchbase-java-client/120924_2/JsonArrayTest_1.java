
    @Test
    public void canPutWhenTypeIsUnknown() {
        Object map = singletonMap("one", 1);
        Object list = singletonList("red");
        JsonArray json = JsonArray.create()
            .add(map)
            .add(list);

        assertEquals(JsonArray.from(map, list), json);
        assertEquals(JsonObject.from(singletonMap("one", 1)), json.getObject(0));
        assertEquals(JsonArray.from(singletonList("red")), json.getArray(1));
    }

