
    @Test
    public void shouldCompressEmptyContent() {
        ByteBuf content = Unpooled.copiedBuffer("", CharsetUtil.UTF_8);

        UpsertRequest request = new UpsertRequest("key", content.copy(), "bucket");
        request.partition((short) 512);
        channel.writeOutbound(request);
        FullBinaryMemcacheRequest outbound = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);

        System.err.println(outbound.content().toString(CharsetUtil.UTF_8));
    }

    @Test
    public void shouldCompressSmallContent() {

    }

    @Test
    public void shouldCompressLargeContent() {

    }

    @Test
    public void shouldDecompressEmptyContent() {

    }

    @Test
    public void shouldDecompressSmallContent() {

    }

    @Test
    public void shouldDecompressLargeContent() {

    }
