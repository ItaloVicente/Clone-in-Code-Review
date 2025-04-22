
    /**
     * @return the latch
     */
    private CountDownLatch getLatch() {
        if (this.latch == null) {
            latch = new CountDownLatch(1);
        }
        return latch;
