
    @Test
    public void testEarlyChunkInSignatureDoesntFail() throws Exception {
        String chunk1 = "{\n" +
                "    \"requestID\": \"7cde0ed9-1844-436d-85b2-a7b9cd12361c\",\n" +
                "    \"clientContextID\": \"sdkd-java\",\n" +
                "    \"signature\": {\n" +
                "  ";
        String chunk2 = "      \"*\": \"*\"\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"default\": {\n" +
                "                \"id\": 375,\n" +
                "                \"tag\": \"n1ql\",\n" +
                "                \"type\": \"n1qldoc\"\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"status\": \"success\",\n" +
                "    \"metrics\": {\n" +
                " ";
        String chunk3 = "       \"elapsedTime\": \"1m18.410321814s\",\n" +
                "        \"executionTime\": \"1m18.410092882s\",\n" +
                "        \"resultCount\": 1,\n" +
                "        \"resultSize\": 100,\n" +
                "        \"mutationCount\": 0,\n" +
                "        \"errorCount\": 0,\n" +
                "        \"warningCount\": 0\n" +
                "    }\n" +
                "}";

        String[] chunks = new String[] { chunk1, chunk2, chunk3 };
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        Object[] httpChunks = new Object[chunks.length + 1];
        httpChunks[0] = responseHeader;
        for (int i = 1; i <= chunks.length; i++) {
            String chunk = chunks[i - 1];
            if (i == chunks.length) {
                httpChunks[i] = new DefaultLastHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            } else {
                httpChunks[i] = new DefaultHttpContent(Unpooled.copiedBuffer(chunk, CharsetUtil.UTF_8));
            }
        }

        Subject<CouchbaseResponse,CouchbaseResponse> obs = AsyncSubject.create();
        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        when(requestMock.observable()).thenReturn(obs);
        queue.add(requestMock);
        channel.writeInbound(httpChunks);
        GenericQueryResponse inbound = (GenericQueryResponse) obs.timeout(1, TimeUnit.SECONDS).toBlocking().last();

        final AtomicInteger found = new AtomicInteger(0);
        final AtomicInteger errors = new AtomicInteger(0);
        assertResponse(inbound, true, ResponseStatus.SUCCESS, "7cde0ed9-1844-436d-85b2-a7b9cd12361c", "sdkd-java", "success",
                "{\"*\":\"*\"}",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        found.incrementAndGet();
                        String content = byteBuf.toString(CharsetUtil.UTF_8);
                        byteBuf.release();
                        assertNotNull(content);
                        assertTrue(!content.isEmpty());
                        try {
                            Map decoded = mapper.readValue(content, Map.class);
                            assertTrue(decoded.size() > 0);
                            assertTrue(decoded.containsKey("default"));
                        } catch (Exception e) {
                            assertTrue(false);
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        buf.release();
                        errors.incrementAndGet();
                    }
                },
                expectedMetricsCounts(0, 1) //these are the numbers parsed from metrics object, not real count
        );
        assertEquals(1, found.get());
        assertEquals(0, errors.get());    }
