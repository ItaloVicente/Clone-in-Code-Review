    /**
     * Update the config if it has changed and notify our
     * observers.
     *
     * @param bucketToMonitor the bucketToMonitor to set
     */
    private void setBucket(Bucket bucket) {
        if (this.bucket == null || !this.bucket.equals(bucket)) {
            this.bucket = bucket;
            setChanged();
            notifyObservers(this.bucket);
        }
    }
