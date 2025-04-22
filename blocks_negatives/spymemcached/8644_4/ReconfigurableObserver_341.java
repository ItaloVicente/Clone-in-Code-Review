    public ReconfigurableObserver(Reconfigurable rec) {
        this.rec = rec;
    }

    /**
     * Delegates update to the reconfigurable passed in the constructor
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {
        rec.reconfigure((Bucket) arg);
    }
