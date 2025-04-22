
    @Override
    public int refCnt() {
        return content.refCnt();
    }

    @Override
    public RemoveDesignDocumentResponse retain() {
        content.retain();
        return this;
    }

    @Override
    public RemoveDesignDocumentResponse retain(int increment) {
        content.retain(increment);
        return this;
    }

    @Override
    public boolean release() {
        return content.release();
    }

    @Override
    public boolean release(int decrement) {
        return content.release(decrement);
    }
