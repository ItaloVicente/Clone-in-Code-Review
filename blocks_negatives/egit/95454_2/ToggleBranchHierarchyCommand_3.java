	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HandlerUtil.toggleCommandState(event.getCommand());
		IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		if (part instanceof RepositoriesView) {
			CommonViewer viewer = ((RepositoriesView) part).getCommonViewer();
			viewer.refresh();
		}
		return null;
	}
