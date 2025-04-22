        assertEquals(1277, inbound1Content.length() + inbound2Content.length() + inbound3Content.length());
        assertTrue(inbound1.content().toString(CharsetUtil.UTF_8).endsWith("}"));
        assertTrue(inbound2.content().toString(CharsetUtil.UTF_8).endsWith("}"));
        assertTrue(inbound3.content().toString(CharsetUtil.UTF_8).endsWith("}"));
    }

    @Test
    public void shouldSupportChunkSplitInString() {
        queue.add(ViewQueryRequest.class);

        String json = Resources.read("with-braces.json", this.getClass());
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpContent content1 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(0, 65), CharsetUtil.UTF_8));
        HttpContent content2 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(66, 391), CharsetUtil.UTF_8));
        HttpContent content3 = new DefaultHttpContent(Unpooled.copiedBuffer(json.substring(392, 522), CharsetUtil.UTF_8));
        HttpContent content4 = new DefaultLastHttpContent(Unpooled.copiedBuffer(json.substring(523, 1308), CharsetUtil.UTF_8));
        channel.writeInbound(response, content1, content2, content3, content4);
