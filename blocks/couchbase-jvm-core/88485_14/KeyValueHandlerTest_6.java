
    @Test
    public void shouldNotCompressEmptyContent() {
        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.copiedBuffer("", CharsetUtil.UTF_8);

        UpsertRequest request = new UpsertRequest("key", content.copy(), "bucket");
        request.partition((short) 512);
        channel.writeOutbound(request);
        FullBinaryMemcacheRequest outbound = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);

        assertEquals(0, outbound.getDataType());
        assertEquals("", outbound.content().toString(CharsetUtil.UTF_8));
    }

    @Test
    public void shouldCompressSmallContent() {
        String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo " +
            "ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient " +
            "montes, nascetur ridiculus mus.";

        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);

        UpsertRequest request = new UpsertRequest("key", content.copy(), "bucket");
        request.partition((short) 512);
        channel.writeOutbound(request);
        FullBinaryMemcacheRequest outbound = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);

        assertEquals(KeyValueHandler.DATATYPE_SNAPPY, outbound.getDataType());

        byte[] compressed = new byte[outbound.content().readableBytes()];
        outbound.content().getBytes(0, compressed);
        byte[] uncompressed = Snappy.uncompress(compressed, 0, compressed.length);
        assertArrayEquals(text.getBytes(CharsetUtil.UTF_8), uncompressed);
    }

    @Test
    public void shouldCompressLargeContent() {
        String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula " +
            "eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, " +
            "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. " +
            "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate " +
            "eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum " +
            "felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper " +
            "nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, " +
            "eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. " +
            "Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam " +
            "ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus." +
            " Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet " +
            "adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, " +
            "lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis " +
            "faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. " +
            "Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget" +
            " bibendum sodales, augue velit cursus nunc,";

        while (text.length() < Short.MAX_VALUE) {
            text += text;
        }

        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);

        UpsertRequest request = new UpsertRequest("key", content.copy(), "bucket");
        request.partition((short) 512);
        channel.writeOutbound(request);
        FullBinaryMemcacheRequest outbound = (FullBinaryMemcacheRequest) channel.readOutbound();
        assertNotNull(outbound);

        assertEquals(KeyValueHandler.DATATYPE_SNAPPY, outbound.getDataType());

        byte[] compressed = new byte[outbound.content().readableBytes()];
        outbound.content().getBytes(0, compressed);
        byte[] uncompressed = Snappy.uncompress(compressed, 0, compressed.length);
        assertArrayEquals(text.getBytes(CharsetUtil.UTF_8), uncompressed);
    }

    @Test
    public void shouldDecompressEmptyContent() {
        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.copiedBuffer("", CharsetUtil.UTF_8);
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse(KEY, Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setDataType(KeyValueHandler.DATATYPE_SNAPPY);

        GetRequest requestMock = mock(GetRequest.class);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals("", event.content().toString(CHARSET));
    }

    @Test
    public void shouldDecompressSmallContent() {
        String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo " +
            "ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient " +
            "montes, nascetur ridiculus mus.";

        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.wrappedBuffer(Snappy.compress(text.getBytes(CharsetUtil.UTF_8)));
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse(KEY, Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setDataType(KeyValueHandler.DATATYPE_SNAPPY);

        GetRequest requestMock = mock(GetRequest.class);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(text, event.content().toString(CHARSET));
    }

    @Test
    public void shouldDecompressLargeContent() {
        String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula " +
            "eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, " +
            "nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. " +
            "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate " +
            "eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum " +
            "felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper " +
            "nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, " +
            "eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. " +
            "Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam " +
            "ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus." +
            " Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet " +
            "adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, " +
            "lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis " +
            "faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. " +
            "Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget" +
            " bibendum sodales, augue velit cursus nunc,";

        while (text.length() < Short.MAX_VALUE) {
            text += text;
        }

        channel.pipeline().addFirst(new SnappyFeatureHandler());

        ByteBuf content = Unpooled.wrappedBuffer(Snappy.compress(text.getBytes(CharsetUtil.UTF_8)));
        FullBinaryMemcacheResponse response = new DefaultFullBinaryMemcacheResponse(KEY, Unpooled.EMPTY_BUFFER,
            content.copy());
        response.setDataType(KeyValueHandler.DATATYPE_SNAPPY);

        GetRequest requestMock = mock(GetRequest.class);
        requestQueue.add(requestMock);
        channel.writeInbound(response);

        GetResponse event = (GetResponse) eventSink.responseEvents().get(0).getMessage();
        assertEquals(text, event.content().toString(CHARSET));
    }

    class SnappyFeatureHandler extends SimpleChannelInboundHandler<FullBinaryMemcacheResponse> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullBinaryMemcacheResponse msg) throws Exception {

        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
            ctx.fireUserEventTriggered(new ServerFeaturesEvent(Collections.singletonList(ServerFeatures.SNAPPY)));
            ctx.pipeline().remove(this);
        }
    }
