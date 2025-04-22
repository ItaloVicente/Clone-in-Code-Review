            if (o instanceof IContainer) {
                IContainer container = (IContainer) o;
                if (!showLinkedResources && container.isLinked(IResource.CHECK_ANCESTORS)) {
                    return EMPTY;
                }
                IResource[] members = null;
                try {
                    members = container.members();
                } catch (CoreException e) {
                    return EMPTY;
                }

                List<IResource> results = new ArrayList<>();
                for (IResource resource : members) {
                    if (!showLinkedResources && resource.isLinked()) {
                        continue;
                    }
                    if ((resource.getType() & resourceType) > 0) {
                        results.add(resource);
                    }
                }
                return results.toArray();
            }
            if (o instanceof ArrayList) {
                return ((List<?>) o).toArray();
            }
            return EMPTY;
        }
    }

    /**
     * Returns a content provider for <code>IResource</code>s that returns
     * only children of the given resource type.
     */
    private ITreeContentProvider getResourceProvider(int resourceType, boolean showLinkedResources) {
        return new ResourceProvider(resourceType, showLinkedResources);
    }

    /**
     * Returns {<code>true</code> if the page tree and list providers should
     * show linked resources. Default is false.
     *
     * @since 3.12
     */
    protected boolean getShowLinkedResources() {
        return showLinkedResources;
    }

    /**
     * Updates the resources tree to show or hide linked resources
     *
     * @param showLinked
     *            {<code>true</code> if the page should show linked resources
     * @since 3.12
     */
    protected void updateContentProviders(boolean showLinked) {
        showLinkedResources = showLinked;
        resourceGroup.setTreeProviders(
                getResourceProvider(IResource.FOLDER | IResource.PROJECT, showLinkedResources),
                WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());

        resourceGroup.setListProviders(getResourceProvider(IResource.FILE, showLinkedResources),
                WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
    }

    /**
     * Returns this page's collection of currently-specified resources to be
     * exported. This is the primary resource selection facility accessor for
     * subclasses.
     *
     * @return a collection of resources currently selected
     * for export (element type: <code>IResource</code>)
     */
    protected List getSelectedResources() {
        Iterator resourcesToExportIterator = this
                .getSelectedResourcesIterator();
        List resourcesToExport = new ArrayList();
        while (resourcesToExportIterator.hasNext()) {
