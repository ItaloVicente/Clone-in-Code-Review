    private static final ObjectMapper CONTROL_MAPPER = new ObjectMapper();
    private JsonTranscoder converter;

    private static Map<String, Object> readJsonIntoMap(final ByteBuf raw) throws Exception {
        return CONTROL_MAPPER.readValue(
            raw.toString(CharsetUtil.UTF_8),
            CONTROL_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class)
        );
    }

    @Before
    public void setup() {
        converter = new JsonTranscoder();
    }

    @Test
    public void shouldEncodeEmptyJsonObject() {
        JsonObject object = JsonObject.empty();

        Tuple2<ByteBuf, Integer> encoded = converter.encode(JsonDocument.create("id", object));
        assertEquals("{}", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeEmptyJsonObject() {
        ByteBuf content = Unpooled.copiedBuffer("{}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);
        assertTrue(decoded.content().isEmpty());
    }

    @Test(expected = TranscodingException.class)
    public void shouldFailToDecodeWithWrongOldFlags() {
        ByteBuf content = Unpooled.copiedBuffer("{}", CharsetUtil.UTF_8);
        int wrongFlags = 1234;
        converter.decode("id", content, 0, 0, wrongFlags, ResponseStatus.SUCCESS);
    }

    @Test(expected = TranscodingException.class)
    public void shouldFailToDecodeWithWrongCommonFlags() {
        ByteBuf content = Unpooled.copiedBuffer("{}", CharsetUtil.UTF_8);
        int wrongFlags = TranscoderUtils.BINARY_COMMON_FLAGS;
        converter.decode("id", content, 0, 0, wrongFlags, ResponseStatus.SUCCESS);
    }

    @Test
    public void shouldEncodeObjectWithEmptyArray() {
        JsonObject object = JsonObject.create();
        object.put("array", JsonArray.create());

        Tuple2<ByteBuf, Integer> encoded = converter.encode(JsonDocument.create("id", object));
        assertEquals("{\"array\":[]}", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }


    @Test
    public void shouldDecodeObjectWithEmptyArray() {
        ByteBuf content = Unpooled.copiedBuffer("{\"array\":[]}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);

        assertFalse(decoded.content().isEmpty());
        assertEquals(1, decoded.content().size());
        assertTrue(decoded.content().getArray("array").isEmpty());
    }

    @Test
    public void shouldEncodeMixedJsonValues() throws Exception {
        JsonObject object = JsonObject.create();
        object.put("string", "Hello World");
        object.put("integer", 1);
        object.put("long", Long.MAX_VALUE);
        object.put("double", 11.3322);
        object.put("boolean", true);

        Tuple2<ByteBuf, Integer> encoded = converter.encode(JsonDocument.create("id", object));
        Map<String, Object> control = readJsonIntoMap(encoded.value1());

        assertEquals(5, control.size());
        assertEquals("Hello World", control.get("string"));
        assertEquals(1, control.get("integer"));
        assertEquals(Long.MAX_VALUE, control.get("long"));
        assertEquals(11.3322, control.get("double"));
        assertEquals(true, control.get("boolean"));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeMixedJsonValues() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("{\"boolean\":true,\"integer\":1,\"string\":\"Hello World\"," +
            "\"double\":11.3322,\"long\":9223372036854775807}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);
        JsonObject found = decoded.content();

        assertFalse(found.isEmpty());
        assertEquals(5, found.size());
        assertEquals(true, found.getBoolean("boolean"));
        assertEquals(1, (int) found.getInt("integer"));
        assertEquals("Hello World", found.getString("string"));
        assertEquals(11.3322, found.getDouble("double"), 0);
        assertEquals(Long.MAX_VALUE, (long) found.getLong("long"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldEncodeNestedObjects() throws Exception {
        JsonObject object = JsonObject.create();
        object.put("empty", JsonObject.create());
        object.put("nested", JsonObject.create().put("item", JsonArray.from("foo", "bar", 1)));

        Tuple2<ByteBuf, Integer> encoded = converter.encode(JsonDocument.create("id", object));
        Map<String, Object> control = readJsonIntoMap(encoded.value1());

        assertEquals(2, control.size());
        assertTrue(((Map) control.get("empty")).isEmpty());

        Map<String, Object> nested = (Map<String, Object>) control.get("nested");
        assertFalse(nested.isEmpty());
        assertEquals(1, nested.size());
        assertEquals(3, ((List) nested.get("item")).size());
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeNestedObjects() {
        ByteBuf content = Unpooled.copiedBuffer("{\"nested\":{\"item\":[\"foo\",\"bar\",1]},\"empty\":{}}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);

        assertFalse(decoded.content().isEmpty());
        assertFalse(decoded.content().getObject("nested").isEmpty());
        assertEquals(3, decoded.content().getObject("nested").getArray("item").size());
    }

    @Test
    public void shouldEncodeNestedArrays() throws Exception {
        JsonObject object = JsonObject.empty();
        object.put("1", JsonArray.create().add(JsonArray.create().add(JsonArray.create().add("Hello World"))));

        Tuple2<ByteBuf, Integer> encoded = converter.encode(JsonDocument.create("id", object));
        assertEquals("{\"1\":[[[\"Hello World\"]]]}", encoded.value1().toString(CharsetUtil.UTF_8));
        assertEquals(TranscoderUtils.JSON_COMPAT_FLAGS, (long) encoded.value2());
    }

    @Test
    public void shouldDecodeNestedArray() {
        ByteBuf content = Unpooled.copiedBuffer("{\"1\":[[[\"Hello World\"]]]}", CharsetUtil.UTF_8);
        JsonDocument decoded = converter.decode("id", content, 0, 0, TranscoderUtils.JSON_COMMON_FLAGS, ResponseStatus.SUCCESS);

        assertFalse(decoded.content().isEmpty());
        assertFalse(decoded.content().getArray("1").isEmpty());
        assertEquals("Hello World", decoded.content().getArray("1").getArray(0).getArray(0).getString(0));
    }
