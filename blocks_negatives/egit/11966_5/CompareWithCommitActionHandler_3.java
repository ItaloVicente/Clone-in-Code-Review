			try {
				CompareUtils.compareWorkspaceWithRef(repo, baseFile, dlg
						.getCommitId().getName(), null);
			} catch (IOException e) {
				Activator.handleError(
								UIText.CompareWithRefAction_errorOnSynchronize,
								e, true);
			}
		} else {
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().showView(
								CompareTreeView.ID);
				view.setInput(resources, dlg.getCommitId().name());
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
