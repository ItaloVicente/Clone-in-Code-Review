
    @Test
    public void shouldExportEscapedJsonValue() {
        JsonArray arr = JsonArray.create().add("\"\b\r\n\f\t\\/");
        String escaped = "\\\"\\b\\r\\n\\f\\t\\\\/";
        assertEquals("[\"" + escaped + "\"]", arr.toString());
    }

    @Test
    public void shouldExportEscapedControlCharInValue() {
        JsonArray arr = JsonArray.create().add("\u001F");
        String escaped = "\\u001F";
        assertEquals("[\"" + escaped + "\"]", arr.toString());
    }

