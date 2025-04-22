		Object singleSelection = null;
		if (HandlerUtil
				.getActivePartChecked(event) instanceof RepositoriesView) {
			List<?> selected = getSelectedNodes(event);
			if (selected.size() == 1) {
				singleSelection = selected.get(0);
			}
		} else {
			RepositoriesView view = getRepositoriesView();
			if (view != null) {
				IStructuredSelection selection = view.getCommonViewer()
						.getStructuredSelection();
				if (selection != null && selection.size() == 1) {
					singleSelection = selection.getFirstElement();
				}
			}
		}
		if (!(singleSelection instanceof RepositoryGroupNode)) {
			return null;
		}
		return ((RepositoryGroupNode) singleSelection).getObject();
	}

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
