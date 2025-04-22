    /**
     * Returns whether the workbench is being closed due to an emergency.
     * When this method returns <code>true</code>, the workbench is in dire
     * straights and cannot continue. Indeed, things are so bad that we cannot
     * even risk a normal workbench close. Workbench advisor methods should
     * always check this flag before attempting to communicate with the user.
     *
     * @return <code>true</code> if the workbench is in the process of being
     * closed under emergency conditions, and <code>false</code> otherwise
     */
    boolean emergencyClosing();
