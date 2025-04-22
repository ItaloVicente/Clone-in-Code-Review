    private Object unmarshallSignature(String value) throws Exception {
        return CouchbaseAsyncBucket.JSON_OBJECT_TRANSCODER.byteBufJsonValueToObject(
                Unpooled.copiedBuffer(value, CharsetUtil.UTF_8));
    }

    @Test
    public void testQuerySignaturesAreCorrectlyUnmarshalled() throws Exception {
        Object stringScalar = unmarshallSignature(" \t\n\r\"a\"");
        Object numberScalar = unmarshallSignature(" \t\n\r\n123");
        Object booleanScalar = unmarshallSignature(" \t\n\r\ntrue");
        Object nullScalar = unmarshallSignature(" \t\n\r\nnull");
        Object jsonObject = unmarshallSignature(" \t\n\r\n{\"a\": 123}");
        Object jsonArray = unmarshallSignature(" \t\n\r\n[1, 2, 3]");

        assertTrue(stringScalar.getClass().getSimpleName(), stringScalar instanceof String);
        assertTrue(numberScalar.getClass().getSimpleName(), numberScalar instanceof Number);
        assertTrue(booleanScalar.getClass().getSimpleName(), booleanScalar instanceof Boolean);
        assertNull(nullScalar);
        assertTrue(jsonObject.getClass().getSimpleName(), jsonObject instanceof JsonObject);
        assertTrue(jsonArray.getClass().getSimpleName(), jsonArray instanceof JsonArray);

        assertEquals("a", stringScalar);
        assertEquals(123, numberScalar);
        assertEquals(true, booleanScalar);
        assertNull(nullScalar);
        assertEquals(JsonObject.create().put("a", 123), jsonObject);
        assertEquals(JsonArray.from(1, 2, 3), jsonArray);
    }

