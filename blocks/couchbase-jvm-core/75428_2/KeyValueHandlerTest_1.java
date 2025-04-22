    @Test
    public void shouldPropagateErrorOnEncode() {
        String id = "key";
        ByteBuf content = Unpooled.buffer();
        content.release(); // provoke a IllegalReferenceCountException
        UpsertRequest request = new UpsertRequest(id, content, BUCKET);
        request.partition((short) 1);


        TestSubscriber<CouchbaseResponse> ts = TestSubscriber.create();
        request.observable().subscribe(ts);

        try {
            channel.writeOutbound(request);
            fail("Expected exception, none thrown.");
        } catch (EncoderException ex) {
            assertTrue(ex.getCause() instanceof IllegalReferenceCountException);
        }

        List<Throwable> onErrorEvents = ts.getOnErrorEvents();
        assertTrue(onErrorEvents.get(0) instanceof RequestCancelledException);
        assertTrue(onErrorEvents.get(0).getCause() instanceof IllegalReferenceCountException);
    }

