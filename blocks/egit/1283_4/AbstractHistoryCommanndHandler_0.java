
	protected GitHistoryPage getPage() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window == null)
			return null;
		if (window.getActivePage() == null)
			return null;
		IWorkbenchPart part = window.getActivePage().getActivePart();
		if (!(part instanceof IHistoryView))
			return null;
		IHistoryView view = (IHistoryView) part;
		IHistoryPage page = view.getHistoryPage();
		if (page instanceof GitHistoryPage)
			return (GitHistoryPage) page;
		return null;
	}

	protected IStructuredSelection getSelection(GitHistoryPage page) {
		ISelection pageSelection = page.getSelectionProvider().getSelection();
		if (pageSelection instanceof IStructuredSelection) {
			return (IStructuredSelection) pageSelection;
		} else
			return new StructuredSelection();
	}
