    @Test
    public void shouldExportMix() {
        JsonObject obj = JsonObject.empty()
            .put("name", "michael")
            .put("age", 25)
            .put("developer", true);
        assertEquals("{\"developer\":true,\"age\":25,\"name\":\"michael\"}", obj.toString());
    }

