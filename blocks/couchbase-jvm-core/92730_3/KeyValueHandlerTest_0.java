                "ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient " +
                "montes, nascetur ridiculus mus.";

        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);

        UpsertRequest request = new UpsertRequest("key", content.copy(), "bucket");
        request.partition((short) 512);
        channel.writeOutbound(request);
        FullBinaryMemcacheRequest outbound = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);

        assertEquals(0, outbound.getDataType());
        ReferenceCountUtil.release(outbound);
    }

    @Test
    public void shouldCompressSmallContent() throws Exception {
        String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.";
        text += text;
