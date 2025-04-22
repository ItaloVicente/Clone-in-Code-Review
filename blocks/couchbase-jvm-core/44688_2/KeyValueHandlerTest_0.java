    @Test
    public void shouldDecodeObserveResponseDuringRebalance() throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("{someconfig...}", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("", Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setStatus(KeyValueHandler.STATUS_NOT_MY_VBUCKET);

        ObserveRequest requestMock = mock(ObserveRequest.class);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        ObserveResponse event = (ObserveResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(ResponseStatus.RETRY, event.status());
        assertEquals(ObserveResponse.ObserveStatus.UNKNOWN, event.observeStatus());
    }

    @Test
    public void shouldDecodeCounterResponseWhenNotSuccessful() throws Exception {
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse("", Unpooled.EMPTY_BUFFER,
            Unpooled.EMPTY_BUFFER);
        response.setStatus(BinaryMemcacheResponseStatus.DELTA_BADVAL);

        CounterRequest requestMock = mock(CounterRequest.class);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        assertEquals(1, eventSink.responseEvents().size());
        CounterResponse event = (CounterResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(ResponseStatus.FAILURE, event.status());
        assertEquals(0, event.value());
    }
