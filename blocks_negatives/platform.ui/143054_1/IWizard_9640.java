    boolean isHelpAvailable();

    /**
     * Returns whether this wizard needs Previous and Next buttons.
     * <p>
     * The result of this method is typically used by the container.
     * </p>
     *
     * @return <code>true</code> if Previous and Next buttons are required,
     *   and <code>false</code> if none are needed
     */
    boolean needsPreviousAndNextButtons();

    /**
     * Returns whether this wizard needs a progress monitor.
     * <p>
     * The result of this method is typically used by the container.
     * </p>
     *
     * @return <code>true</code> if a progress monitor is required,
     *   and <code>false</code> if none is needed
     */
    boolean needsProgressMonitor();

    /**
     * Performs any actions appropriate in response to the user
     * having pressed the Cancel button, or refuse if canceling
     * now is not permitted.
     *
     * @return <code>true</code> to indicate the cancel request
     *   was accepted, and <code>false</code> to indicate
     *   that the cancel request was refused
     */
    boolean performCancel();

    /**
     * Performs any actions appropriate in response to the user
     * having pressed the Finish button, or refuse if finishing
     * now is not permitted.
     *
     * Normally this method is only called on the container's
     * current wizard. However if the current wizard is a nested wizard
     * this method will also be called on all wizards in its parent chain.
     * Such parents may use this notification to save state etc. However,
     * the value the parents return from this method is ignored.
     *
     * @return <code>true</code> to indicate the finish request
     *   was accepted, and <code>false</code> to indicate
     *   that the finish request was refused
     */
    boolean performFinish();

    /**
     * Sets or clears the container of this wizard.
     *
     * @param wizardContainer the wizard container, or <code>null</code>
     */
    void setContainer(IWizardContainer wizardContainer);
