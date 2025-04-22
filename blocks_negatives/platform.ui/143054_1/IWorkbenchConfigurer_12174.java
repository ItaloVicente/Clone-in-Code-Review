    /**
     * Restores the workbench state saved from the previous session, if any.
     * This includes any open windows and their open perspectives, open views
     * and editors, layout information, and any customizations to the open
     * perspectives.
     * <p>
     * This is typically called from the advisor's <code>openWindows()</code>
     * method.
     * </p>
     *
     * @return a status object indicating whether the restore was successful
     * @see #RESTORE_CODE_RESET
     * @see #RESTORE_CODE_EXIT
     * @see WorkbenchAdvisor#openWindows
     */
    IStatus restoreState();
