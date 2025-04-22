    /**
     * Return whether the progress monitor has been canceled.
     *
     * @return <code>true</code> if the monitor has been cancelled, <code>false</code> otherwise.
     */
    public boolean isCanceled() {
        if (monitor == null) {
            return canceled;
        }
