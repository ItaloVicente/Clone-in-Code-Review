    @Override
    public MemcacheContent retainedDuplicate() {
        return duplicate().retain();
    }

    @Override
    public MemcacheContent touch() {
        content.touch();
        return this;
    }

    @Override
    public MemcacheContent touch(Object o) {
        content.touch(o);
        return this;
    }

    @Override
    public MemcacheContent replace(ByteBuf content) {
        return new DefaultMemcacheContent(content);
    }

