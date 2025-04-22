
    @Override
    public short partition() {
        if (partition == -1) {
            throw new IllegalStateException("Partition requested but not set beforehand");
        }
        return partition;
    }

    @Override
    public DCPResponse partition(short partition) {
        if (partition < 0) {
            throw new IllegalArgumentException("Partition must be larger than or equal to zero");
        }
        this.partition = partition;
        return this;
    }
