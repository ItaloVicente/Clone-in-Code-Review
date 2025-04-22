        IStatus result = workspace.validatePath(testPath.toString(),
                IResource.PROJECT | IResource.FOLDER | IResource.ROOT);
        if (result.isOK()) {
            return testPath;
        }

        return null;
    }

    /**
     * Returns a content provider for <code>FileSystemElement</code>s that returns
     * only files as children.
     */
    protected abstract ITreeContentProvider getFileProvider();

    /**
     * Returns a content provider for <code>FileSystemElement</code>s that returns
     * only folders as children.
     */
    protected abstract ITreeContentProvider getFolderProvider();

    /**
     * Return the path for the resource field.
     * @return IPath
     */
    protected IPath getResourcePath() {
        if (this.containerNameField != null) {
            return getPathFromText(this.containerNameField);
        }

        if (this.initialContainerFieldValue != null && this.initialContainerFieldValue.length() > 0) {
            return new Path(this.initialContainerFieldValue).makeAbsolute();
        }

        return Path.EMPTY;
    }

    /**
     * Returns this page's list of currently-specified resources to be
     * imported. This is the primary resource selection facility accessor for
     * subclasses.
     *
     * @return a list of resources currently selected
     * for export (element type: <code>IResource</code>)
     */
    protected java.util.List getSelectedResources() {
        return this.selectionGroup.getAllCheckedListItems();
    }

    /**
     * Returns this page's list of currently-specified resources to be
     * imported filtered by the IElementFilter.
     * @since 3.10
     */
    protected void getSelectedResources(IElementFilter filter, IProgressMonitor monitor) throws InterruptedException {
        this.selectionGroup.getAllCheckedListItems(filter, monitor);
    }

    /**
     * <bold>DO NOT USE THIS METHOD</bold>
     * Returns this page's list of currently-specified resources to be
     * imported filtered by the IElementFilter.
     * @deprecated Should use the API {@link IElementFilter}
     */
    @Deprecated
	protected void getSelectedResources(org.eclipse.ui.internal.ide.dialogs.IElementFilter filter, IProgressMonitor monitor) throws InterruptedException {
        this.selectionGroup.getAllCheckedListItems(filter, monitor);
    }

    /**
     * Returns the container resource specified in the container name entry field,
     * or <code>null</code> if such a container does not exist in the workbench.
     *
     * @return the container resource specified in the container name entry field,
     *   or <code>null</code>
     */
    protected IContainer getSpecifiedContainer() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
        IPath path = getContainerFullPath();
        if (workspace.getRoot().exists(path)){
        	IResource resource = workspace.getRoot().findMember(path);
        	if(resource.getType() == IResource.FILE) {
