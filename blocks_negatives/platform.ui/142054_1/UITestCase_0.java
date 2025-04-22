     * Adds a window listener to the workbench to keep track of
     * opened test windows.
     */
    private void addWindowListener() {
        windowListener = new TestWindowListener();
        fWorkbench.addWindowListener(windowListener);
    }

    /**
     * Removes the listener added by <code>addWindowListener</code>.
     */
    private void removeWindowListener() {
        if (windowListener != null) {
            fWorkbench.removeWindowListener(windowListener);
        }
    }

    /**
     * Outputs a trace message to the trace output device, if enabled.
     * By default, trace messages are sent to <code>System.out</code>.
     *
     * @param msg the trace message
     */
    protected void trace(String msg) {
        System.out.println(msg);
    }

    /**
