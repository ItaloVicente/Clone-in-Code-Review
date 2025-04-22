        assertEquals(0, errors.get());
    }

    @Test
    public void testBigChunkedResponseWithEscapedBackslashInRowObject() throws Exception {
        String response = Resources.read("chunkedResponseWithDoubleBackslashes.txt", this.getClass());
        String[] chunks = response.split("(?m)^[0-9a-f]+");

        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        responseHeader.headers().add("Transfer-Encoding", "chunked");
        responseHeader.headers().add("Content-Type", "application/json; version=1.0.0");
        Object[] httpChunks = new Object[chunks.length];
        httpChunks[0] = responseHeader;
        for (int i = 1; i < chunks.length; i++) {
            String chunk = chunks[i];
            if (i == chunks.length - 1) {
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
        inbound.rows().timeout(5, TimeUnit.SECONDS).toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                int rowNumber = found.incrementAndGet();
                String content = byteBuf.toString(CharsetUtil.UTF_8);
                byteBuf.release();
                assertNotNull(content);
                assertFalse(content.isEmpty());
            }
        });

        inbound.errors().timeout(5, TimeUnit.SECONDS).toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf buf) {
                buf.release();
                errors.incrementAndGet();
            }
        });

        String status = inbound.queryStatus().timeout(5, TimeUnit.SECONDS).toBlocking().single();

        List<ByteBuf> metricList = inbound.info().timeout(1, TimeUnit.SECONDS).toList().toBlocking().single();
        assertEquals(1, metricList.size());
        String metricsJson = metricList.get(0).toString(CharsetUtil.UTF_8);
        Map metrics = mapper.readValue(metricsJson, Map.class);

        assertEquals("success", status);

        assertEquals(5, found.get());
        assertEquals(0, errors.get());

        assertEquals(found.get(), metrics.get("resultCount"));
    }
