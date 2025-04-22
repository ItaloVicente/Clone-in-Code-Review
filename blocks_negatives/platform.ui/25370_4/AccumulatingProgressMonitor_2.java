    /**
     * Clears the collector object used to accumulate work and subtask calls
     * if it matches the given one.
     * @param collectorToClear
     */
    private synchronized void clearCollector(Collector collectorToClear) {
        if (this.collector == collectorToClear) {
