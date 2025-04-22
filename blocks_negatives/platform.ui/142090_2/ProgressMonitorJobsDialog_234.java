            IRunnableWithProgress runnable) throws InvocationTargetException,
            InterruptedException {
        if (!fork) {
            enableDetails(false);
        }
        super.run(fork, cancelable, runnable);
    }

    /**
     * Set the enable state of the details button now or when it will be
     * created.
     *
     * @param enableState
     *            a boolean to indicate the preferred' state
     */
    protected void enableDetails(boolean enableState) {
        if (detailsButton == null) {
