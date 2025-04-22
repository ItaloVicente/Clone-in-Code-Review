
    @Test
    public void shouldEncodeAndDecodeSubJson() {
        JsonObject sub1 = JsonObject.create().put("item1", 1).put("item2", 2);
        JsonArray sub2 = JsonArray.create().add("item3");
        JsonObject obj = JsonObject.create().put("a", sub1).put("b", sub2);
        JsonDocument doc = JsonDocument.create("test", obj);

        Tuple2<ByteBuf, Integer> encoded = converter.encode(doc);
        System.err.println(encoded.value1().toString(CharsetUtil.UTF_8));
        JsonDocument decoded = converter.decode("test", encoded.value1(), 0, 0, encoded.value2(),
                ResponseStatus.SUCCESS);

        assertNotNull(decoded.content());
        assertEquals(2, decoded.content().size());
        assertEquals(sub1, decoded.content().get("a"));
    }
