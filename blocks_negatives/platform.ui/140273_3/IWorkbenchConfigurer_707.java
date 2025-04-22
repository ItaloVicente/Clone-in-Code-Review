    /**
     * Forces the workbench to close due to an emergency. This method should
     * only be called when the workbench is in dire straights and cannot
     * continue, and cannot even risk a normal workbench close (think "out of
     * memory" or "unable to create shell"). When this method is called, an
     * abbreviated workbench shutdown sequence is performed (less critical
     * steps may be skipped). The workbench advisor is still called; however,
     * it must not attempt to communicate with the user. While an emergency
     * close is in progress, <code>emergencyClosing</code> returns
     * <code>true</code>. Workbench advisor methods should always check this
     * flag before communicating with the user.
     *
     * @see #emergencyClosing
     */
    void emergencyClose();
