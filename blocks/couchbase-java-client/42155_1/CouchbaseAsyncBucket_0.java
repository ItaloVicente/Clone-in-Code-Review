    protected final void safeCheck() {
        if (closed) {
            throw new BucketClosedException("Reference to bucket " + this.name() + " has been closed");
        }
    }

