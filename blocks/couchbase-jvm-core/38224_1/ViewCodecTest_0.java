        ViewQueryResponse inbound1 = (ViewQueryResponse) channel.readInbound();
        ViewQueryResponse inbound2 = (ViewQueryResponse) channel.readInbound();

        assertEquals(ResponseStatus.CHUNKED, inbound1.status());
        assertEquals(ResponseStatus.SUCCESS, inbound2.status());
        assertEquals(632, inbound1.content().readableBytes() + inbound2.content().readableBytes());
        assertTrue(inbound1.content().toString(CharsetUtil.UTF_8).endsWith("}"));
        assertTrue(inbound2.content().toString(CharsetUtil.UTF_8).endsWith("}"));
    }

    @Test
    public void shouldSupportContentChunksWitBraces() {
        queue.add(ViewQueryRequest.class);

        String json = Resources.read("with-braces.json", this.getClass());
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpContent content1 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(0, 10), CharsetUtil.UTF_8));
        HttpContent content2 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(11, 320), CharsetUtil.UTF_8));
        HttpContent content3 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(321, 662), CharsetUtil.UTF_8));
        HttpContent content4 = new DefaultLastHttpContent(Unpooled.copiedBuffer(json.substring(663, 1308), CharsetUtil.UTF_8));
        channel.writeInbound(response, content1, content2, content3, content4);

