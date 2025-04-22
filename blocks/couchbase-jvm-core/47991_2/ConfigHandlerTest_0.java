    @Test
    public void shouldNotBreakLinesOnLongAuth() throws Exception {
        String longPassword = "thisIsAveryLongPasswordWhichShouldNotContainLineBreaksAfterEncodingOtherwise"
            + "itWillBreakTheRequestResponseFlowWithTheServer";
        BucketConfigRequest request = new BucketConfigRequest("/path/", InetAddress.getLocalHost(), "bucket",
            longPassword);

        channel.writeOutbound(request);
        HttpRequest outbound = (HttpRequest) channel.readOutbound();

        assertEquals(HttpMethod.GET, outbound.getMethod());
        assertEquals(HttpVersion.HTTP_1_1, outbound.getProtocolVersion());
        assertEquals("/path/bucket", outbound.getUri());
        assertTrue(outbound.headers().contains(HttpHeaders.Names.AUTHORIZATION));
        assertNotNull(outbound.headers().get(HttpHeaders.Names.AUTHORIZATION));
        assertEquals("Couchbase Client Mock", outbound.headers().get(HttpHeaders.Names.USER_AGENT));
    }

