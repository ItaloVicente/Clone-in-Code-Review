    @Test
    public void shouldNotBreakLinesOnLongAuth() {
        String longPassword = "thisIsAveryLongPasswordWhichShouldNotContainLineBreaksAfterEncodingOtherwise"
            + "itWillBreakTheRequestResponseFlowWithTheServer";
        GetDesignDocumentRequest request = new GetDesignDocumentRequest("name", true, "bucket", longPassword);

        channel.writeOutbound(request);
        HttpRequest outbound = (HttpRequest) channel.readOutbound();

        assertEquals(HttpMethod.GET, outbound.getMethod());
        assertEquals(HttpVersion.HTTP_1_1, outbound.getProtocolVersion());
        assertEquals("/bucket/_design/dev_name", outbound.getUri());
        assertTrue(outbound.headers().contains(HttpHeaders.Names.AUTHORIZATION));
        assertNotNull(outbound.headers().get(HttpHeaders.Names.AUTHORIZATION));
        assertEquals("Couchbase Client Mock", outbound.headers().get(HttpHeaders.Names.USER_AGENT));
    }

