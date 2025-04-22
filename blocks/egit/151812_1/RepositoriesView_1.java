	private void handleRenameContext(ISelection selection,
			CommonViewer viewer) {
		if (selection == null || selection.isEmpty()
				|| !(selection instanceof StructuredSelection)) {
			if (renameContext != null) {
				ctxSrv.deactivateContext(renameContext);
				renameContext = null;
			}
			return;
		}
		StructuredSelection sel = (StructuredSelection) selection;
		Object item = sel.getFirstElement();
		if (sel.size() != 1 || !(item instanceof RepositoryGroupNode)) {
			if (renameContext != null) {
				ctxSrv.deactivateContext(renameContext);
				renameContext = null;
			}
		} else if (viewer.getTree().isFocusControl()) {
			renameContext = ctxSrv
					.activateContext(VIEW_ID + RENAME_GROUP_CONTEXT);
		}
	}

