        assertEquals(ResponseStatus.CHUNKED, inbound1.status());
        assertEquals(ResponseStatus.CHUNKED, inbound2.status());
        assertEquals(ResponseStatus.SUCCESS, inbound3.status());

        String inbound1Content = inbound1.content().toString(CharsetUtil.UTF_8);
        String inbound2Content = inbound2.content().toString(CharsetUtil.UTF_8);
        String inbound3Content = inbound3.content().toString(CharsetUtil.UTF_8);
