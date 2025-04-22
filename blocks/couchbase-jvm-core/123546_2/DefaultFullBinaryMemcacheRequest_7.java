        return replace(content().duplicate());
    }

    @Override
    public FullBinaryMemcacheRequest retainedDuplicate() {
        return duplicate().retain();
    }

    @Override
    public FullBinaryMemcacheRequest replace(ByteBuf content) {
