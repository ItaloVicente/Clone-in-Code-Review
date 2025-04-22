    @Override
    public long serverDuration() {
        return serverDuration;
    }

    @Override
    public BinaryResponse serverDuration(long duration) {
        this.serverDuration = duration;
        return this;
    }

