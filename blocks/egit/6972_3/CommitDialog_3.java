	private static CellLabelProvider createStatusLabelProvider() {
		CommitStatusLabelProvider baseProvider = new CommitStatusLabelProvider();
		ProblemLabelDecorator decorator = new ProblemLabelDecorator(null);
		return new DecoratingStyledCellLabelProvider(baseProvider, decorator, null) {
			@Override
			public String getToolTipText(Object element) {
				return ((CommitItem) element).status.getText();
			}
		};
	}

