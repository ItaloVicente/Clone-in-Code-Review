        selectionChanged(StructuredSelection.EMPTY);
    }

    /**
     * Creates the delegate and refreshes its enablement.
     */
    protected final void createDelegate() {
        if (delegate == null && runAttribute != null) {
            try {
                Object obj = WorkbenchPlugin.createExtension(configElement,
                        runAttribute);
                delegate = validateDelegate(obj);
                initDelegate();
                refreshEnablement();
            } catch (Throwable e) {
                runAttribute = null;
                IStatus status = null;
                if (e instanceof CoreException) {
                    status = ((CoreException) e).getStatus();
                } else {
                    status = StatusUtil
                            .newStatus(
                                    IStatus.ERROR,
                                    "Internal plug-in action delegate error on creation.", e); //$NON-NLS-1$
                }
                String id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                WorkbenchPlugin
                        .log(
                                "Could not create action delegate for id: " + id, status); //$NON-NLS-1$
                return;
            }
        }
    }

    /**
     * Validates the object is a delegate of the expected type. Subclasses can
     * override to check for specific delegate types.
     * <p>
     * <b>Note:</b> Calls to the object are not allowed during this method.
     * </p>
     *
     * @param obj a possible action delegate implementation
     * @return the <code>IActionDelegate</code> implementation for the object
     * @throws WorkbenchException if not of the expected delegate type
     */
    protected IActionDelegate validateDelegate(Object obj)
            throws WorkbenchException {
        if (obj instanceof IActionDelegate) {
