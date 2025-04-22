    public void shouldDecodePartiallyFailedViewQueryResponse() throws Exception {
        String response = Resources.read("query_partially_failed.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk1 = new DefaultHttpContent(Unpooled.copiedBuffer(response.substring(0, 500), CharsetUtil.UTF_8));
        HttpContent responseChunk2 = new DefaultHttpContent(Unpooled.copiedBuffer(response.substring(500, 1000), CharsetUtil.UTF_8));
        HttpContent responseChunk3 = new DefaultLastHttpContent(Unpooled.copiedBuffer(response.substring(1000), CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk1, responseChunk2, responseChunk3);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);

        assertTrue(inbound.status().isSuccess());

        final AtomicInteger calledRow = new AtomicInteger();
        inbound.rows().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                calledRow.incrementAndGet();
                try {
                    Map found = mapper.readValue(byteBuf.toString(CharsetUtil.UTF_8), Map.class);
                    assertEquals(3, found.size());
                    assertTrue(found.containsKey("id"));
                    assertTrue(found.containsKey("key"));
                    assertTrue(found.containsKey("value"));
                } catch (IOException e) {
                    e.printStackTrace();
                    assertFalse(true);
                }
            }
        });
        assertEquals(5, calledRow.get());

        final AtomicInteger called = new AtomicInteger();
        inbound.info().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                called.incrementAndGet();
                assertEquals("{\n" +
                                "    \"total_rows\": 7303,\n" +
                                "    \"errors\": [\n" +
                                "        {\n" +
                                "            \"from\": \"http://192.168.1.80:9503/_view_merge/?stale=false\",\n" +
                                "            \"reason\": \"req_timedout\"\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"from\": \"http://192.168.1.80:9502/_view_merge/?stale=false\",\n" +
                                "            \"reason\": \"req_timedout\"\n" +
                                "        }\n" +
                                "    ]}\n" +
                                "    ",
                        byteBuf.toString(CharsetUtil.UTF_8));
            }
        });
        assertEquals(1, called.get());
    }

    @Test
    public void shouldDecodeNotFoundViewQueryResponse() throws Exception {
        String response = Resources.read("query_view_not_found.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(404, "Not found"));
        HttpContent responseBody = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        ViewQueryRequest requestMock = mock(ViewQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseBody);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        ViewQueryResponse inbound = (ViewQueryResponse) firedEvents.get(0);

        assertFalse(inbound.status().isSuccess());
