    /**
     * Runs the given runnable on the given display as soon as possible. Unlike
     * greedyExec, this has no effect if the given runnable has already been
     * scheduled for execution. Use this method to schedule work that will
     * affect the way one or more wigdets are drawn, but that should only happen
     * once.
     *
     * <p>
     * This is threadsafe.
     * </p>
     *
     * @param d
     *            display
     * @param r
     *            runnable to execute in the UI thread. Has no effect if the
     *            given runnable has already been scheduled but has not yet run.
     */
    public static void runOnce(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }
        WorkQueue queue = getQueueFor(d);
        queue.runOnce(r);
    }
