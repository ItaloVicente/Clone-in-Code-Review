	public ResourceNavigatorRenameAction(Shell shell, TreeViewer treeViewer) {
		super(shell, treeViewer.getTree());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				INavigatorHelpContextIds.RESOURCE_NAVIGATOR_RENAME_ACTION);
		this.viewer = treeViewer;
	}
