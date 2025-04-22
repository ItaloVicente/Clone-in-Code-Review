    /**
     * Asks the user for the destination of this action.
     *
     * @return the path on an existing or new resource container, or
     *  <code>null</code> if the operation should be abandoned
     */
    IPath queryDestinationResource() {
        ContainerSelectionDialog dialog = new ContainerSelectionDialog(shellProvider.getShell(),
                getInitialContainer(), true, IDEWorkbenchMessages.CopyResourceAction_selectDestination);
        dialog.setValidator(this);
        dialog.showClosedProjects(false);
        dialog.open();
        Object[] result = dialog.getResult();
        if (result != null && result.length == 1) {
            return (IPath) result[0];
        }
        return null;
    }
