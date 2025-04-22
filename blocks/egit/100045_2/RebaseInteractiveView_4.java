		switchRepositoriesAction = new RepositoryToolbarAction(false,
				repo -> setInput(new StructuredSelection(repo)));
		toolbar.add(switchRepositoriesAction);

		UIUtils.notifySelectionChangedWithCurrentSelection(
				selectionChangedListener, getSite());

		getSite().setSelectionProvider(new RepositorySelectionProvider(
				planTreeViewer, () -> currentRepository));

