        assertTrue(outbound instanceof FullHttpRequest);
        assertEquals("query", ((FullHttpRequest) outbound).content().toString(CharsetUtil.UTF_8));
        if (jsonExpected) {
            assertEquals("application/json", outbound.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        } else {
            assertNotEquals("application/json", outbound.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
    }

    @Test
    public void shouldEncodeSimpleStatementToGenericQueryRequest() {
        GenericQueryRequest request = GenericQueryRequest.simpleStatement("query", "bucket", "password");
        assertGenericQueryRequest(request, false);
    }

    @Test
    public void shouldEncodeJsonQueryToGenericQueryRequest() {
        GenericQueryRequest request = GenericQueryRequest.jsonQuery("query", "bucket", "password");
        assertGenericQueryRequest(request, true);
