	private static final String[] VIEWS_TO_CLOSE = { //
			RebaseInteractiveView.VIEW_ID, //
			ISynchronizeView.VIEW_ID, //
			IHistoryView.VIEW_ID, //
			CompareTreeView.ID, //
			ReflogView.VIEW_ID, //
			StagingView.VIEW_ID, //
			RepositoriesView.VIEW_ID, //
			"org.eclipse.search.ui.views.SearchView", //
			"org.eclipse.ui.views.PropertySheet"
	};

	@Rule
	public TestName testName = new TestName();

