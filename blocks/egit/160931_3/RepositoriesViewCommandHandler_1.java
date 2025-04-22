	}

	private RepositoriesView getRepositoriesView() {
		return (RepositoriesView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findViewReference(RepositoriesView.VIEW_ID).getView(false);
