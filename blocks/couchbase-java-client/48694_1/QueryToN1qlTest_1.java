    private Object unmarshallSignature(String value) throws Exception {
        return CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.byteBufJsonValueToObject(
                Unpooled.copiedBuffer(value, CharsetUtil.UTF_8));
    }

    @Test
    public void testQuerySignaturesAreCorrectlyUnmarshalled() throws Exception {
        Object stringScalar = unmarshallSignature("\"a\"");
        Object numberScalar = unmarshallSignature("123");
        Object booleanScalar = unmarshallSignature("true");
        Object nullScalar = unmarshallSignature("null");
        Object jsonObject = unmarshallSignature("{\"a\": 123}");
        Object jsonArray = unmarshallSignature("[1, 2, 3]");

        assertTrue(stringScalar.getClass().getSimpleName(), stringScalar instanceof String);
        assertTrue(numberScalar.getClass().getSimpleName(), numberScalar instanceof Number);
        assertTrue(booleanScalar.getClass().getSimpleName(), booleanScalar instanceof Boolean);
        assertNull(nullScalar);
        assertTrue(jsonObject.getClass().getSimpleName(), jsonObject instanceof JsonObject);
        assertTrue(jsonArray.getClass().getSimpleName(), jsonArray instanceof JsonArray);

        assertEquals(JsonObject.create().put("a", 123), jsonObject);
        assertEquals(JsonArray.from(1, 2, 3), jsonArray);
    }

