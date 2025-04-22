    @Override
    public byte getFramingExtrasLength() {
        return framingExtrasLength;
    }

    @Override
    public BinaryMemcacheMessage setFramingExtrasLength(byte framingExtrasLength) {
        this.framingExtrasLength = framingExtrasLength;
        return this;
    }

    @Override
    public ByteBuf getFramingExtras() {
        return framingExtras;
    }

    @Override
    public BinaryMemcacheMessage setFramingExtras(ByteBuf framingExtras) {
        this.framingExtras = framingExtras;
        return this;
    }
