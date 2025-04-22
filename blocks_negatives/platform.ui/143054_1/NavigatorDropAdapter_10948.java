    /**
     * Returns an error status with the given info.
     */
    private IStatus error(String message) {
        return error(message, null);
    }

    /**
     * Returns an error status with the given info.
     */
    private IStatus error(String message, Throwable exception) {
        return new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, 0, message,
                exception);
    }

    /**
     * Returns the actual target of the drop, given the resource
     * under the mouse.  If the mouse target is a file, then the drop actually
     * occurs in its parent.  If the drop location is before or after the
     * mouse target and feedback is enabled, the target is also the parent.
     */
    private IContainer getActualTarget(IResource mouseTarget) {
        /* if cursor is before or after mouseTarget, set target to parent */
        if (getFeedbackEnabled()) {
            if (getCurrentLocation() == LOCATION_BEFORE
                    || getCurrentLocation() == LOCATION_AFTER) {
                return mouseTarget.getParent();
            }
        }
        /* if cursor is on a file, return the parent */
        if (mouseTarget.getType() == IResource.FILE) {
            return mouseTarget.getParent();
        }
        /* otherwise the mouseTarget is the real target */
        return (IContainer) mouseTarget;
    }

    /**
     * Returns the display
     */
    private Display getDisplay() {
        return getViewer().getControl().getDisplay();
    }

    /**
     * Returns the resource selection from the LocalSelectionTransfer.
     *
     * @return the resource selection from the LocalSelectionTransfer
     */
    private IResource[] getSelectedResources() {
