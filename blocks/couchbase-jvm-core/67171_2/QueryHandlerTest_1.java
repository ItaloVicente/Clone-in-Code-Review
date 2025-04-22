

    @Test
    public void testSplitAtStatusWithEmptyResponse() {
        String chunk1 = "{\n" +
                "    \"requestID\": \"826e33cb-af29-4002-8a40-ef90915e05b1\",\n" +
                "    \"clientContextID\": \"$$637\",\n" +
                "    \"signature\": {\n" +
                "        \"doc\": \"json\"\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "    ],\n" +
                "    \"sta";
        String chunk2 = "tus\": \"success\",\n" +
                "    \"metrics\": {\n" +
                "        \"elapsedTime\": \"4.69718ms\",\n" +
                "        \"executionTime\": \"4.582526ms\",\n" +
                "        \"resultCount\": 0,\n" +
                "        \"resultSize\": 0\n" +
                "    }\n" +
                "}";

        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        responseHeader.headers().add("Transfer-Encoding", "chunked");
        responseHeader.headers().add("Content-Type", "application/json; version=1.0.0");
        Object[] httpChunks = new Object[3];
        httpChunks[0] = responseHeader;
        httpChunks[1] = new DefaultHttpContent(Unpooled.copiedBuffer(chunk1, CharsetUtil.UTF_8));
        httpChunks[2] = new DefaultLastHttpContent(Unpooled.copiedBuffer(chunk2, CharsetUtil.UTF_8));

        Subject<CouchbaseResponse,CouchbaseResponse> obs = AsyncSubject.create();
        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        when(requestMock.observable()).thenReturn(obs);
        queue.add(requestMock);
        channel.writeInbound(httpChunks);
        Exception error = null;
        GenericQueryResponse inbound = null;
        try {
            inbound = (GenericQueryResponse) obs.timeout(1, TimeUnit.SECONDS).toBlocking().last();
            inbound.info().timeout(1, TimeUnit.SECONDS).toBlocking().last();
        } catch (Exception e) {
            error = e;
        }

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(inbound).isNotNull();
        softly.assertThat(error).isNull();
        softly.assertThat(handler.getDecodingState()).isEqualTo(DecodingState.INITIAL);
        softly.assertThat(handler.getQueryParsingState()).isEqualTo(QueryHandler.QUERY_STATE_INITIAL);
        softly.assertAll();
    }
