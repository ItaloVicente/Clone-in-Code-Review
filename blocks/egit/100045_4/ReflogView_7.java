		IToolBarManager toolbar = getViewSite().getActionBars()
				.getToolBarManager();
		switchRepositoriesAction = new RepositoryToolbarAction(false,
				repo -> reactOnSelection(new StructuredSelection(repo)));
		toolbar.add(switchRepositoriesAction);
		getViewSite().getActionBars().updateActionBars();
