    @Test
    public void shouldDecodeOneRowResponseWithShortClientID() throws Exception {
        String response = Resources.read("short_client_id.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, "123456789", "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map found = mapper.readValue(response, Map.class);
                            assertEquals(12, found.size());
                            assertEquals("San Francisco", found.get("city"));
                            assertEquals("United States", found.get("country"));
                            Map geo = (Map) found.get("geo");
                            assertNotNull(geo);
                            assertEquals(3, geo.size());
                            assertEquals("ROOFTOP", geo.get("accuracy"));
                        } catch (IOException e) {
                            assertFalse(true);
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(1, invokeCounter1.get());
    }

    @Test
    public void shouldDecodeOneRowResponseWithNoClientID() throws Exception {
        String response = Resources.read("no_client_id.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, "", "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map found = mapper.readValue(response, Map.class);
                            assertEquals(12, found.size());
                            assertEquals("San Francisco", found.get("city"));
                            assertEquals("United States", found.get("country"));
                            Map geo = (Map) found.get("geo");
                            assertNotNull(geo);
                            assertEquals(3, geo.size());
                            assertEquals("ROOFTOP", geo.get("accuracy"));
                        } catch (IOException e) {
                            assertFalse(true);
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(1, invokeCounter1.get());
    }

    @Test
    public void shouldDecodeOneRowResponseWithoutPrettyPrint() throws Exception {
        String response = Resources.read("no_pretty.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map found = mapper.readValue(response, Map.class);
                            assertEquals(12, found.size());
                            assertEquals("San Francisco", found.get("city"));
                            assertEquals("United States", found.get("country"));
                            Map geo = (Map) found.get("geo");
                            assertNotNull(geo);
                            assertEquals(3, geo.size());
                            assertEquals("ROOFTOP", geo.get("accuracy"));
                        } catch (IOException e) {
                            assertFalse(true);
                        }
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(1, invokeCounter1.get());
    }

    @Test
    public void shouldGroupErrorsAndWarnings() throws InterruptedException {
        String response = Resources.read("errors_and_warnings.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        Map<String, Object> expectedMetrics = expectedMetricsCounts(1, 0);
        expectedMetrics.put("warningCount", 1);

        final AtomicInteger count = new AtomicInteger(0);
        assertResponse(inbound, false, ResponseStatus.FAILURE, FAKE_REQUESTID, FAKE_CLIENTID, "fatal",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf byteBuf) {
                        fail("no result expected");
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        count.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        try {
                            Map error = mapper.readValue(response, Map.class);
                            assertEquals(5, error.size());
                            if (count.get() == 1) {
                                assertEquals(new Integer(4100), error.get("code"));
                                assertEquals(Boolean.FALSE, error.get("temp"));
                                assertEquals("Parse Error", error.get("msg"));
                            } else if (count.get() == 2) {
                                assertEquals(3, error.get("sev"));
                                assertEquals(201, error.get("code"));
                                assertEquals(Boolean.TRUE, error.get("temp"));
                                assertEquals("Nothing to do", error.get("msg"));
                                assertEquals("nothingToDo", error.get("name"));
                            }
                        } catch (IOException e) {
                            fail();
                        }
                    }
                },
                expectedMetrics
        );
        assertEquals(2, count.get());
    }
