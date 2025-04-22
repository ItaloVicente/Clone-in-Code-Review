	private static CellLabelProvider createStatusLabelProvider() {
		CommitStatusLabelProvider baseProvider = new CommitStatusLabelProvider();
		ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
		return new DecoratingStyledCellLabelProvider(baseProvider, decorator, null) {
			@Override
			public String getToolTipText(Object element) {
				return ((CommitItem) element).status.getText();
			}
		};
	}

