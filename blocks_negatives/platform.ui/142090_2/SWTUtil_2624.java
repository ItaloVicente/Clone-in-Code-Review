    /**
     * Cancels a greedyExec or runOnce that was previously scheduled on the
     * given display. Has no effect if the given runnable is not in the queue
     * for the given display
     *
     * @param d
     *            target display
     * @param r
     *            runnable to execute
     */
    public static void cancelExec(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }
        WorkQueue queue = getQueueFor(d);
        queue.cancelExec(r);
    }
