	protected void expandRepositoryGroup(ExecutionEvent event,
			RepositoryGroup group) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		if (part instanceof RepositoriesView) {
			((RepositoriesView) part).expandNodeForGroup(group);
		}
	}

