
    @Test
    public void shouldConvertFromStringJson() {
        String someJson = "{\"test\": 123, \"string\": \"value\", \"bool\": true, \"subarray\": [ 123 ], \"subobj\":"
                + "{ \"sub\": \"obj\" }}";
        JsonObject expected = JsonObject.create()
                .put("test", 123)
                .put("string", "value")
                .put("bool", true)
                .put("subarray", JsonArray.from(123))
                .put("subobj", JsonObject.create().put("sub", "obj"));

        JsonObject converted = JsonObject.fromJson(someJson);

        assertEquals(expected, converted);
    }

    @Test
    public void shouldConvertNullStringToNull() {
        assertNull(JsonObject.fromJson("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertBadJsonString() {
        String badJson = "This is not \"JSON\"!";
        JsonObject.fromJson(badJson);
    }

    @Test
    public void shouldFailToConvertNonObjectJson() {
        String bad1 = "true";
        String bad2 = "123";
        String bad3 = "\"string\"";
        String bad4 = "[ 123 ]";

        try { JsonObject.fromJson(bad1); fail(); } catch (IllegalArgumentException e) { }
        try { JsonObject.fromJson(bad2); fail(); } catch (IllegalArgumentException e) { }
        try { JsonObject.fromJson(bad3); fail(); } catch (IllegalArgumentException e) { }
        try { JsonObject.fromJson(bad4); fail(); } catch (IllegalArgumentException e) { }
    }
