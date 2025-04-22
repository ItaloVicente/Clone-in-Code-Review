    @Override
    public int refCnt() {
        return 1;
    }

    @Override
    public AbstractCouchbaseResponse retain() {
        return this;
    }

    @Override
    public AbstractCouchbaseResponse retain(int increment) {
        return this;
    }

    @Override
    public boolean release() {
        return false;
    }

    @Override
    public boolean release(int decrement) {
        return false;
    }

