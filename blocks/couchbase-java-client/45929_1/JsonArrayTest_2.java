    @Test
    public void shouldConvertFromStringJson() {
        String someJson = "[ 123, \"value\", true, [ 123 ], { \"sub\": \"obj\" }]";
        JsonArray expected = JsonArray.create()
                                        .add(123)
                                        .add("value")
                                        .add(true)
                                        .add(JsonArray.from(123))
                                        .add(JsonObject.create().put("sub", "obj"));

        JsonArray converted = JsonArray.parse(someJson);

        assertEquals(expected, converted);
    }

    @Test
    public void shouldConvertNullStringToNull() {
        assertNull(JsonArray.parse("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertBadJsonString() {
        String badJson = "This is not \"JSON\"!";
        JsonObject.parse(badJson);
    }

    @Test
    public void shouldFailToConvertNonArrayJson() {
        String bad1 = "true";
        String bad2 = "123";
        String bad3 = "\"string\"";
        String bad4 = "{\"some\": \"value\"}";

        try { JsonArray.parse(bad1); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.parse(bad2); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.parse(bad3); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.parse(bad4); fail(); } catch (IllegalArgumentException e) { }
    }
