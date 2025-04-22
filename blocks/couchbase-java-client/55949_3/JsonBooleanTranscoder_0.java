    private static final ByteBuf TRUE = Unpooled.unreleasableBuffer(
        Unpooled.wrappedBuffer("true".getBytes(CharsetUtil.UTF_8))
    );
    private static final ByteBuf FALSE = Unpooled.unreleasableBuffer(
        Unpooled.wrappedBuffer("false".getBytes(CharsetUtil.UTF_8))
    );

