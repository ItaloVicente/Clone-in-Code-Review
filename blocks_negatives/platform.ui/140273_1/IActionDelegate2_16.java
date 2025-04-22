    /**
     * Performs this action, passing the SWT event which triggered it. This
     * method is called by the proxy action when the action has been triggered.
     * Implement this method to do the actual work.
     * <p>
     * <b>Note:</b> This method is called instead of <code>run(IAction)</code>.
     * </p>
     *
     * @param action the action proxy that handles the presentation portion of
     * the action
     * @param event the SWT event which triggered this action being run
     * @since 2.0
     */
    void runWithEvent(IAction action, Event event);
