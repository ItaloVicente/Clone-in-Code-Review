    /**
     * Runs the given runnable on the given display as soon as possible. If
     * possible, the runnable will be executed before the next widget is
     * repainted, but this behavior is not guaranteed. Use this method to
     * schedule work will affect the way one or more widgets are drawn.
     *
     * <p>
     * This is threadsafe.
     * </p>
     *
     * @param d
     *            display
     * @param r
     *            runnable to execute in the UI thread.
     */
    public static void greedyExec(Display d, Runnable r) {
        if (d.isDisposed()) {
            return;
        }
