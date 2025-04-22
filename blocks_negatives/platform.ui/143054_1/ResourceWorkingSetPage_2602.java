        if (workingSet != null) {
            text.setText(workingSet.getName());
        }
        setPageComplete(false);

        Dialog.applyDialogFont(composite);
    }

    /**
     * Collects all checked resources in the specified container.
     *
     * @param checkedResources the output, list of checked resources
     * @param container the container to collect checked resources in
     */
    private void findCheckedResources(List checkedResources,
            IContainer container) {
        IResource[] resources = null;
        try {
            resources = container.members();
        } catch (CoreException ex) {
            handleCoreException(
                    ex,
                    getShell(),
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error,
                    IDEWorkbenchMessages.ResourceWorkingSetPage_error_updateCheckedState);
        }
        for (IResource resource : resources) {
            if (tree.getGrayed(resource)) {
                if (resource.isAccessible()) {
					findCheckedResources(checkedResources,
                            (IContainer) resource);
