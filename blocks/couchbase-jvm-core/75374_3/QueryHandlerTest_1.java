
    @Test
    public void shouldDecodeCorrectlyOnContinousEscapedCharacters() throws Exception {
        String response = Resources.read("with_multiple_escaped_quotes.json", this.getClass());
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
                FAKE_SIGNATURE,
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        String response = buf.toString(CharsetUtil.UTF_8);
                        ReferenceCountUtil.releaseLater(buf);
                        try {
                            Map found = mapper.readValue(response, Map.class);
                            assertEquals("Map expected count", 1, found.size());
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
                expectedMetricsCounts(0, 0)
        );
        assertEquals(1, invokeCounter1.get());
    }
