    @Test
    public void shouldExportEscapedJsonValue() {
        JsonObject obj = JsonObject.create().put("escapeSimple", "\"\b\r\n\f\t\\/");
        String escaped = "\\\"\\b\\r\\n\\f\\t\\\\/";
        assertEquals("{\"escapeSimple\":\"" + escaped + "\"}", obj.toString());
    }

    @Test
    public void shouldExportEscapedJsonAttribute() {
        JsonObject obj = JsonObject.create().put("\"\b\r\n\f\t\\/", "escapeSimpleValue");
        String escaped = "\\\"\\b\\r\\n\\f\\t\\\\/";
        assertEquals("{\"" + escaped + "\":\"escapeSimpleValue\"}", obj.toString());
    }

    @Test
    public void shouldExportEscapedControlCharInValue() {
        JsonObject obj = JsonObject.create().put("controlChar", "\u001F");
        String escaped = "\\u001F";
        assertEquals("{\"controlChar\":\"" + escaped + "\"}", obj.toString());
    }

    @Test
    public void shouldExportEscapedControlCharInAttribute() {
        JsonObject obj = JsonObject.create().put("\u001F", "controlCharValue");
        String escaped = "\\u001F";
        assertEquals("{\"" + escaped + "\":\"controlCharValue\"}", obj.toString());
    }

