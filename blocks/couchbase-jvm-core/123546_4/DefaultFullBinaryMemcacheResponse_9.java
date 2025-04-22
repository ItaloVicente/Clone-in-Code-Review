        return replace(content().duplicate());
    }

    @Override
    public FullBinaryMemcacheResponse setContent(ByteBuf content) {
        this.content = content;
        return this;
    }

    @Override
    public FullBinaryMemcacheResponse retainedDuplicate() {
        return duplicate().retain();
    }

    @Override
    public FullBinaryMemcacheResponse replace(ByteBuf content) {
