    @Test
    public void shouldDecodeSuccess1NoMetrics() throws Exception {
        String response = Resources.read("success_1_noMetrics.json", this.getClass());
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
        assertResponse(inbound, true, ResponseStatus.SUCCESS, "ff226b49-9d4c-415b-8428-263cb080e184", "", "success", "{\"*\":\"*\"}",
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
                null
        );
        assertEquals(1, invokeCounter1.get());
        assertEquals("{\"adHoc_N1qlQuery492841478131758\":{\"item\":\"value\"}}", items.get(0).replaceAll("\\s", ""));
    }

