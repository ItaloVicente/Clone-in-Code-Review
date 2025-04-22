    boolean snappyEnabled = false;


    static class SnappyEncoder extends SnappyFramedEncoder {
        @Override
        public void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
            super.encode(ctx, in, out);
        }
    }

    static class SnappyDecoder extends SnappyFramedDecoder {
        @Override
        public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            super.decode(ctx, in, out);
        }
    }

    private final Snappy snappy = new Snappy();

