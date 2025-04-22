    /**
     * Performs this action, passing the SWT event which triggered it.
     * <p>
     * This method is called when the delegating action has been triggered.
     * Implement this method to do the actual work.
     * If an action delegate implements this interface, this method
     * is called instead of <code>run(IAction)</code>.
     * <p>
     *
     * @param action the action proxy that handles the presentation portion of the action
     * @param event the SWT event which triggered this action being run
     * @since  2.0
     * @deprecated Use org.eclipse.ui.IActionDelegate2 instead.
     */
    @Deprecated
	public void runWithEvent(IAction action, Event event);
