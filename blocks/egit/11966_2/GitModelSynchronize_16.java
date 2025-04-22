	private static void openGitTreeCompare(IResource[] resources,
			String srcRev, String dstRev) {
		CompareTreeView view;
		try {
			view = (CompareTreeView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView(CompareTreeView.ID);
			view.setInput(resources, srcRev, dstRev);
		} catch (PartInitException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
