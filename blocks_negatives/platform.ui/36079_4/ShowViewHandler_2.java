	private final void openView(final String viewId,
			final IWorkbenchWindow activeWorkbenchWindow)
			throws PartInitException {

		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		activePage.showView(viewId);
		
