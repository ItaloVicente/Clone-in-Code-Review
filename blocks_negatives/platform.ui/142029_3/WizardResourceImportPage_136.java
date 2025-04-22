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
