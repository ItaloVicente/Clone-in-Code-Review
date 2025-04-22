	private static void openGitTreeCompare(IResource[] resources,
			String srcRev, String dstRev, boolean includeLocal) {
		CompareTreeView view;
		try {
			view = (CompareTreeView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView(CompareTreeView.ID);
			if (includeLocal)
				view.setInput(resources, dstRev);
			else
				view.setInput(resources, srcRev, dstRev);
		} catch (PartInitException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
