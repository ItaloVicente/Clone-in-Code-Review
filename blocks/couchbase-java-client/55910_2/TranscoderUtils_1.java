    public static ByteBuf encodeStringAsUtf8(String source) {
        ByteBuf target = Unpooled.buffer(source.length());
        ByteBufUtil.writeUtf8(target, source);
        return target;
    }

