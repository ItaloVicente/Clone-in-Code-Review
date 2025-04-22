	private ISelectionListener mailFolderChangedListener = new ISelectionListener() {
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (part instanceof FoldersView && selection instanceof StructuredSelection) {
				Object selected = ((TreeSelection) selection).getFirstElement();
				if (selected instanceof TreeItem && ((TreeItem) selected).getValue() instanceof FolderType) {
					updateMailFolder((FolderType) ((TreeItem) selected).getValue());
				}
