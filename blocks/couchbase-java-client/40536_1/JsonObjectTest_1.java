    @Test
    public void shouldReturnNullWhenNotFound() {
        JsonObject obj = JsonObject.empty();
        assertNull(obj.getInt("notfound"));
    }

