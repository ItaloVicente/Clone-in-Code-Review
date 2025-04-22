
    @Test
    public void shouldDecodeSimpleStringAsSignature() throws Exception {
        String response = Resources.read("signature_simple_string.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success", "\"json\"",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String item = buf.toString(CharsetUtil.UTF_8);
                        buf.release();
                        fail("no result expected, got " + item);
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        buf.release();
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(0, invokeCounter1.get());
    }

    @Test
    public void shouldDecodeNullAsSignature() throws Exception {
        String response = Resources.read("signature_null.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success", "null",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String item = buf.toString(CharsetUtil.UTF_8);
                        buf.release();
                        fail("no result expected, got " + item);
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        buf.release();
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(0, invokeCounter1.get());
    }

    @Test
    public void shouldDecodeBooleanAsSignature() throws Exception {
        String response = Resources.read("signature_scalar.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        assertResponse(inbound, true, ResponseStatus.SUCCESS, FAKE_REQUESTID, FAKE_CLIENTID, "success", "true",
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String item = buf.toString(CharsetUtil.UTF_8);
                        buf.release();
                        fail("no result expected, got " + item);
                    }
                },
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        buf.release();
                        fail("no error expected");
                    }
                },
                expectedMetricsCounts(0, 1)
        );
        assertEquals(0, invokeCounter1.get());
    }
