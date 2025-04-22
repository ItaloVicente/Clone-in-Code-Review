
    @Test
    public void shouldEncodeAndDecodeSubJson() {
        JsonObject sub1 = JsonObject.create().put("item1", 1).put("item2", 2);
        JsonArray sub2 = JsonArray.create().add("item3");
        JsonArray arr = JsonArray.create().add(sub1).add(sub2);
        JsonArrayDocument doc = JsonArrayDocument.create("test", arr);

        Tuple2<ByteBuf, Integer> encoded = converter.encode(doc);
        System.err.println(encoded.value1().toString(CharsetUtil.UTF_8));
        JsonArrayDocument decoded = converter.decode("test", encoded.value1(), 0, 0, encoded.value2(),
                ResponseStatus.SUCCESS);

        assertNotNull(decoded.content());
        assertEquals(2, decoded.content().size());
        assertEquals(sub1, decoded.content().get(0));
        assertEquals(sub2, decoded.content().get(1));
    }
