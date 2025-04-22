	}

	private MenuManager getActionMenuManager(ExtendedTextEditor editor)
			throws Throwable {
		fPage
				.showActionSet("org.eclipse.ui.tests.internal.TextSelectionActions");
		WorkbenchWindow win = (WorkbenchWindow) fWindow;
		IContributionItem item = win.getMenuBarManager().find(
				"org.eclipse.ui.tests.internal.TextSelectionMenu");
		while (item instanceof SubContributionItem) {
			item = ((SubContributionItem) item).getInnerItem();
			if (item instanceof MenuManager) {
