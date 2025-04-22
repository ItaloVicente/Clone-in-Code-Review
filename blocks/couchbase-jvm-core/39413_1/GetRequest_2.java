        if (lock && touch) {
            throw new IllegalArgumentException("Locking and touching in the same request is not supported");
        }
        this.lock = lock;
        this.touch = touch;
        this.expiry = expiry;
    }

    public boolean lock() {
        return lock;
    }

    public boolean touch() {
        return touch;
