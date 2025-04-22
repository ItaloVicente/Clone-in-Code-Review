
    @Test
    public void shouldDecodeNRowResponseSmallyChunked() throws Exception {
        String response = Resources.read("chunked.json", this.getClass());
        String[] chunks = new String[] {
                response.substring(0, 48),
                response.substring(48, 84),
                response.substring(84, 144),
                response.substring(144, 258),
                response.substring(258, 438),
                response.substring(438, 564),
                response.substring(564, 702),
                response.substring(702, 740),
                response.substring(740)
        };

        StringBuilder sb = new StringBuilder("Chunks:");
        for (String chunk : chunks) {
            sb.append("\n>").append(chunk);
        }
        LOGGER.info(sb.toString());

        shouldDecodeChunked(chunks);
    }

    @Test
    public void shouldDecodeChunkedResponseSplitAtEveryPosition() throws Exception {
        String response = Resources.read("chunked.json", this.getClass());
        for (int i = 1; i < response.length() - 1; i++) {
            String chunk1 = response.substring(0, i);
            String chunk2 = response.substring(i);

            shouldDecodeChunked(chunk1, chunk2);
            LOGGER.info("Decoded response with chunk at position " + i);
        }
    }

    private void shouldDecodeChunked(String... chunks) throws Exception {
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
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, "123456\\\"78901234567890", "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        found.incrementAndGet();
                        String content = byteBuf.toString(CharsetUtil.UTF_8);
                        assertNotNull(content);
                        assertTrue(!content.isEmpty());
                        try {
                            Map decoded = mapper.readValue(content, Map.class);
                            assertTrue(decoded.size() > 0);
                            assertTrue(decoded.containsKey("horseName"));
                        } catch (Exception e) {
                            assertTrue(false);
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        errors.incrementAndGet();
                    }
                },
                expectedMetricsCounts(5678, 1234) //these are the numbers parsed from metrics object, not real count
        );
        assertEquals(5, found.get());
        assertEquals(4, errors.get());
    }
