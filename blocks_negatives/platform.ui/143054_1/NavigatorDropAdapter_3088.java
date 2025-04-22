                }
            }
        }
        return selectedResources.toArray(new IResource[selectedResources.size()]);
    }

    /**
     * Returns the shell
     */
    private Shell getShell() {
        return getViewer().getControl().getShell();
    }

    /**
     * Returns an error status with the given info.
     */
    private IStatus info(String message) {
        return new Status(IStatus.INFO, PlatformUI.PLUGIN_ID, 0, message, null);
    }

    /**
     * Adds the given status to the list of problems.  Discards
     * OK statuses.  If the status is a multi-status, only its children
     * are added.
     */
    private void mergeStatus(MultiStatus status, IStatus toMerge) {
        if (!toMerge.isOK()) {
            status.merge(toMerge);
        }
    }

    /**
     * Returns an status indicating success.
     */
    private IStatus ok() {
        return new Status(IStatus.OK, PlatformUI.PLUGIN_ID, 0,
                ResourceNavigatorMessages.DropAdapter_ok, null);
    }

    /**
     * Opens an error dialog if necessary.  Takes care of
     * complex rules necessary for making the error dialog look nice.
     */
    private void openError(IStatus status) {
        if (status == null) {
