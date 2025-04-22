	}

	private RepositoriesView getRepositoriesView() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewReference ref = page.findViewReference(RepositoriesView.VIEW_ID);
		return ref == null ? null : (RepositoriesView) ref.getView(false);
