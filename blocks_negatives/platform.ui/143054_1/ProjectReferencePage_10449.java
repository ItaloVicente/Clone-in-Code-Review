            error = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
                    1, msg, target);
        }
        ErrorDialog.openError(getControl().getShell(), null, null, error);
    }

    /**
     * Initializes a ProjectReferencePage.
     */
    private void initialize() {
