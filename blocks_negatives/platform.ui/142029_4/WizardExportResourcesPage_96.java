        BusyIndicator.showWhile(getShell().getDisplay(), runnable);

    }

    /**
     * Set up the selection values for the resources and put them in the selectionMap.
     * If a resource is a file see if it matches one of the selected extensions. If not
     * then check the children.
     */
    private void setupSelectionsBasedOnSelectedTypes(Map selectionMap,
            IContainer parent) {

        List selections = new ArrayList();
        IResource[] resources;
        boolean hasFiles = false;

        try {
            resources = parent.members();
        } catch (CoreException exception) {
            return;
        }

        for (IResource resource : resources) {
            if (resource.getType() == IResource.FILE) {
                if (hasExportableExtension(resource.getName())) {
                    hasFiles = true;
                    selections.add(resource);
                }
            } else {
                setupSelectionsBasedOnSelectedTypes(selectionMap,
                        (IContainer) resource);
            }
        }

        if (hasFiles) {
