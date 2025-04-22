    @Test
    public void shouldConvertFromStringJson() {
        String someJson = "[ 123, \"value\", true, [ 123 ], { \"sub\": \"obj\" }]";
        JsonArray expected = JsonArray.create()
                                        .add(123)
                                        .add("value")
                                        .add(true)
                                        .add(JsonArray.from(123))
                                        .add(JsonObject.create().put("sub", "obj"));

        JsonArray converted = JsonArray.fromJson(someJson);

        assertEquals(expected, converted);
    }

    @Test
    public void shouldConvertNullStringToNull() {
        assertNull(JsonArray.fromJson("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertBadJsonString() {
        String badJson = "This is not \"JSON\"!";
        JsonArray.fromJson(badJson);
    }

    @Test
    public void shouldFailToConvertNonArrayJson() {
        String bad1 = "true";
        String bad2 = "123";
        String bad3 = "\"string\"";
        String bad4 = "{\"some\": \"value\"}";

        try { JsonArray.fromJson(bad1); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.fromJson(bad2); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.fromJson(bad3); fail(); } catch (IllegalArgumentException e) { }
        try { JsonArray.fromJson(bad4); fail(); } catch (IllegalArgumentException e) { }
    }
