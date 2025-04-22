
		final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if (activePage == null) {
			return;
		}

		activePage.showView(viewId);
		
