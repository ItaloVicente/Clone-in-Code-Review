	private void handleSingleRepositoryContext(ISelection selection,
			CommonViewer viewer) {
		boolean activate = false;
		if (selection != null && !selection.isEmpty()
				&& (selection instanceof StructuredSelection)) {
			StructuredSelection sel = (StructuredSelection) selection;
			Object item = sel.getFirstElement();
			activate = sel.size() == 1 && (item instanceof RepositoryNode);
		}
		if (!activate) {
			if (renameContext != null) {
				ctxSrv.deactivateContext(renameContext);
				renameContext = null;
			}
		} else if (viewer.getTree().isFocusControl() && renameContext == null) {
			renameContext = ctxSrv.activateContext(SINGLE_REPO_CONTEXT_ID);
		}
	}

