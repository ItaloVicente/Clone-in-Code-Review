
	private static IWorkbenchAdapter getRepositoryTreeNodeWorkbenchAdapter(
			final RepositoryTreeNode node) {
		return new WorkbenchAdapter() {
			@Override
			public String getLabel(Object object) {
				ILabelProvider labelProvider= new RepositoriesViewLabelProvider();
				return labelProvider.getText(node);
			}
		};
	}
