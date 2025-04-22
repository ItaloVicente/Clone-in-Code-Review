        ChannelFuture futureMock = createMock(ChannelFuture.class);
        replay(futureMock);

        BucketUpdateResponseHandler handler = new BucketUpdateResponseHandler();
        PrivateAccessor.setField(handler, "receivedFuture", futureMock);
        PrivateAccessor.invoke(handler, "setReceivedFuture",
                new Class[]{ChannelFuture.class}, new Object[]{futureMock});
        assertEquals(futureMock, PrivateAccessor.getField(handler, "receivedFuture"));

        verify(futureMock);
