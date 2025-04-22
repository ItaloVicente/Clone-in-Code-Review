    /**
     * Table to hold arbitrary key-data settings (key type: <code>String</code>,
     * value type: <code>Object</code>).
     * @see #setData
     */
    private Map extraData = new HashMap();

    /**
     * Indicates whether workbench state should be saved on close and
     * restored on subsequent open.
     */
    private boolean saveAndRestore = false;

    /**
     * Indicates whether the workbench is being force to close. During
     * an emergency close, no interaction with the user should be done.
     */
    private boolean isEmergencyClosing = false;

    /**
     * Indicates the behaviour when the last window is closed.
     * If <code>true</code>, the workbench will exit (saving the last window's state,
     * if configured to do so).
     * If <code>false</code> the window will be closed, leaving the workbench running.
     *
     * @since 3.1
     */
