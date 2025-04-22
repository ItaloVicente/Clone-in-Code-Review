		IWorkbenchWindow window = PlatformUI
				.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IContributionService cs = window.getService(IContributionService.class);
			treeViewer.setComparator(cs.getComparatorFor(getContributionType()));
		}
