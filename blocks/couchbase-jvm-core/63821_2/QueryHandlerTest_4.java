    @Test
    public void shouldDecodeRawJsonResults() throws Exception {
        String response = Resources.read("success_with_raw.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final List<String> items = Collections.synchronizedList(new ArrayList<String>(11));
        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success", "\"json\"",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        String item = buf.toString(CharsetUtil.UTF_8).trim();
                        System.out.println("item #" + invokeCounter1.incrementAndGet() + " = " + item);
                        items.add(item);
                        buf.release();
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        buf.release();
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 8)
        );
        List<String> expectedItems = Arrays.asList("\"usertable:userAA\"", "\"usertable:user1\"",
                "\"usertable:user,2]\"", "null", "\"usertable:user3\"","\"u,s,e,r,t,a,\\\"b,l,e:userBBB\\\\\"","123","\"usertable:user4\"");
        assertEquals(8, invokeCounter1.get());
        assertEquals(expectedItems, items);
    }

    @Test
    public void shouldDecodeChunkedResponseSplitAtEveryPositionWithRaw() throws Throwable {
        String response = Resources.read("success_with_raw.json", this.getClass());
        for (int i = 1; i < response.length() - 1; i++) {
            String chunk1 = response.substring(0, i);
            String chunk2 = response.substring(i);

            try {
                shouldDecodeChunkedWithRaw(chunk1, chunk2);
            } catch (Throwable t) {
                LOGGER.info("Test failed in decoding response with raw, chunked at position " + i);
                throw t;
            }
        }
    }

    private void shouldDecodeChunkedWithRaw(String... chunks) throws Exception {
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
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, "1234567890123456789012345678901234567890123456789012345678901234", "success",
                "\"json\"",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        found.incrementAndGet();
                        String content = byteBuf.toString(CharsetUtil.UTF_8);
                        byteBuf.release();
                        assertNotNull(content);
                        assertTrue(!content.isEmpty());
                        try {
                            Object object = mapper.readValue(content, Object.class);
                            boolean expected = object instanceof Integer || object == null ||
                                    (object instanceof String && ((String) object).startsWith("usertable")) ||
                                    (object instanceof String && ((String) object).startsWith("u,s,e,r"));
                            assertTrue(expected);

                        } catch (Exception e) {
                            e.printStackTrace();
                            fail();
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
                expectedMetricsCounts(0, 8) //these are the numbers parsed from metrics object, not real count
        );
        assertEquals(8, found.get());
        assertEquals(0, errors.get());
    }

