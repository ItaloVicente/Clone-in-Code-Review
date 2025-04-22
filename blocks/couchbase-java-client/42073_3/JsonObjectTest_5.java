    @Test
    public void shouldEqualBasedOnItsProperties() {
        JsonObject obj1 = JsonObject.create().put("foo", "bar");
        JsonObject obj2 = JsonObject.create().put("foo", "bar");
        assertEquals(obj1, obj2);

        obj1 = JsonObject.create().put("foo", "baz");
        obj2 = JsonObject.create().put("foo", "bar");
        assertNotEquals(obj1, obj2);

        obj1 = JsonObject.create().put("foo", "bar").put("bar", "baz");
        obj2 = JsonObject.create().put("foo", "bar");
        assertNotEquals(obj1, obj2);
    }

