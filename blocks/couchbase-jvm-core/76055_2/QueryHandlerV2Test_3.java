
    @Test
    public void shouldDecodeProfileInfo() throws Exception {
        String response = Resources.read("with_timings_profile.json", this.getClass());
        HttpResponse responseHeader = new DefaultHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(200, "OK"));
        HttpContent responseChunk = new DefaultLastHttpContent(Unpooled.copiedBuffer(response, CharsetUtil.UTF_8));

        GenericQueryRequest requestMock = mock(GenericQueryRequest.class);
        queue.add(requestMock);
        channel.writeInbound(responseHeader, responseChunk);
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, firedEvents.size());
        GenericQueryResponse inbound = (GenericQueryResponse) firedEvents.get(0);

        final AtomicInteger invokeCounter1 = new AtomicInteger();
        latch = new CountDownLatch(1);
        inbound.profileInfo().subscribe(
                new Action1<ByteBuf>() {
                    @Override
                    public void call(ByteBuf buf) {
                        invokeCounter1.incrementAndGet();
                        buf.release();
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable err) {
                        latch.countDown();
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        latch.countDown();
                    }
                }
        );
        latch.await(1, TimeUnit.SECONDS);
        assertEquals(1, invokeCounter1.get());
    }
