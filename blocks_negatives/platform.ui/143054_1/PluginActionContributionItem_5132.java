        if (identifier == null) {
            IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
                    .getWorkbench().getActivitySupport();
            IPluginContribution contribution = (IPluginContribution) getAction();
            identifier = workbenchActivitySupport.getActivityManager()
                    .getIdentifier(
                            WorkbenchActivityHelper
                                    .createUnifiedId(contribution));
        }
        return identifier;
    }

    /**
     * Dispose of the IIdentifier if necessary.
     *
     * @since 3.0
     */
    private void disposeIdentifier() {
        identifier = null;
    }

    /**
     * The default implementation of this <code>IContributionItem</code>
     * method notifies the delegate if loaded and implements the <code>IActionDelegate2</code>
     * interface.
     */
    @Override
