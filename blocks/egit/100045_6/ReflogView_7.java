		IToolBarManager toolbar = getViewSite().getActionBars()
				.getToolBarManager();
		switchRepositoriesAction = new RepositoryToolbarAction(false,
				() -> getRepository(),
				repo -> reactOnSelection(new StructuredSelection(repo)));
		toolbar.add(switchRepositoriesAction);
		getViewSite().getActionBars().updateActionBars();
