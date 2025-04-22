	void hideAndAssertNoParts() {
		IWorkbenchWindow[] windows = fWorkbench.getWorkbenchWindows();
		for (IWorkbenchWindow w : windows) {
			IWorkbenchPage ap = w.getActivePage();
			hideAndAssertNoParts(ap);
		}
	}

	void hideAndAssertNoParts(IWorkbenchPage page) {
		IViewReference[] viewReferences = page.getViewReferences();
		for (IViewReference view : viewReferences) {
			page.hideView(view);
		}
		page.closeAllEditors(false);
		processEvents();
		assertNull(page.getActivePart());
	}

