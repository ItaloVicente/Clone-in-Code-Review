	private static IWorkbenchAdapter getRepsitoryNodeWorkbenchAdapter(final RepositoryNode node) {
		return new WorkbenchAdapter() {
			@Override
			public String getLabel(Object object) {
				ILabelProvider labelProvider= new RepositoriesViewLabelProvider();
				return labelProvider.getText(node);
			}
		};
	}
