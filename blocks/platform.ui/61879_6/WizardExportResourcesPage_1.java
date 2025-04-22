    protected boolean getShowLinkedResources() {
        return showLinkedResources;
    }

    protected void updateContentProviders(boolean showLinked) {
        showLinkedResources = showLinked;
        resourceGroup.setTreeProviders(
                getResourceProvider(IResource.FOLDER | IResource.PROJECT, showLinkedResources),
                WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());

        resourceGroup.setListProviders(getResourceProvider(IResource.FILE, showLinkedResources),
                WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
