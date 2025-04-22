	protected void expandRepositoryGroup(ExecutionEvent event,
			RepositoryGroup group) throws ExecutionException {
		if (group != null) {
			RepositoriesView view = null;
			IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
			if (part instanceof RepositoriesView) {
				view = ((RepositoriesView) part);
			} else {
				view = getRepositoriesView();
			}
			if (view != null) {
				view.expandNodeForGroup(group);
			}
		}
	}

	private RepositoriesView getRepositoriesView() {
		return (RepositoriesView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findViewReference(RepositoriesView.VIEW_ID).getView(false);
	}

