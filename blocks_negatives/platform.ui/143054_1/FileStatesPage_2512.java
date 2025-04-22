        applyDialogFont(composite);

        return composite;
    }

    /**
     * Get the Workspace Description this page is operating on.
     * @return org.eclipse.core.resources.IWorkspaceDescription
     */
    private IWorkspaceDescription getWorkspaceDescription() {
        return ResourcesPlugin.getWorkspace().getDescription();
    }

    /**
     * Sent when an event that the receiver has registered for occurs.
     *
     * @param event the event which occurred
     */
    @Override
