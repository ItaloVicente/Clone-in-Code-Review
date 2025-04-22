    /**
     * Runs the given test runnable.
     * The default implementation simply invokes <code>run</code> on the
     * given test runnable.  Subclasses may extend.
     *
     * @param testRunnable the test runnable to run
     */
    public void runTest(Runnable testRunnable) {
        testRunnable.run();
    }
