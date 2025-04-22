	@Deprecated
	protected void getSelectedResources(org.eclipse.ui.internal.ide.dialogs.IElementFilter filter,
			IProgressMonitor monitor) throws InterruptedException {
		this.selectionGroup.getAllCheckedListItems(filter, monitor);
	}

	protected IContainer getSpecifiedContainer() {
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
		IPath path = getContainerFullPath();
		if (workspace.getRoot().exists(path)) {
			IResource resource = workspace.getRoot().findMember(path);
			if (resource.getType() == IResource.FILE) {
