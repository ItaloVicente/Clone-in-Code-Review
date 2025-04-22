	private void createLocalToolbarActions() {
		final IToolBarManager barManager = getSite().getActionBars()
				.getToolBarManager();
		IAction a;

		a = createFindToolbarAction();
		barManager.add(a);
	}

