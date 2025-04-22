		if (locations.length == 1 && !locations[0].toFile().isDirectory()) {
			final IPath baseLocation = locations[0];
			final ITypedElement base = getBaseTypeElement(baseLocation);
			final ITypedElement next;
			try {
				next = getIndexTypedElement(baseLocation);
			} catch (IOException e) {
				Activator.handleError(
						UIText.CompareWithIndexAction_errorOnAddToIndex, e,
						true);
				return null;
			}

			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, null);
			IWorkbenchPage workBenchPage = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
			CompareUtils.openInCompare(workBenchPage, in);
		} else {
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().showView(
								CompareTreeView.ID);
				view.setInput(getSelectedResources(event), CompareTreeView.INDEX_VERSION);
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
