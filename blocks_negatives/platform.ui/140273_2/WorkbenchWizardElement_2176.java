    /**
     * Create a new instance of this class
     *
     * @param configurationElement
     * @since 3.1
     */
    public WorkbenchWizardElement(IConfigurationElement configurationElement) {
        this.configurationElement = configurationElement;
        id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
    }

    /**
     * Answer a boolean indicating whether the receiver is able to handle the
     * passed selection
     *
     * @return boolean
     * @param selection
     *            IStructuredSelection
     */
    public boolean canHandleSelection(IStructuredSelection selection) {
        return getSelectionEnabler().isEnabledForSelection(selection);
    }

    /**
     * Answer the selection for the receiver based on whether the it can handle
     * the selection. If it can return the selection. If it can handle the
     * adapted to IResource value of the selection. If it satisfies neither of
     * these conditions return an empty IStructuredSelection.
     *
     * @return IStructuredSelection
     * @param selection
     *            IStructuredSelection
     */
    @Override
