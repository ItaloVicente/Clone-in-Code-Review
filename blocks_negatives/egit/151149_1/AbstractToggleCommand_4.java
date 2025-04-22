		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IViewPart part = page.findView(RepositoriesView.VIEW_ID);
				if (part instanceof RepositoriesView) {
					(((RepositoriesView) part).getCommonViewer()).refresh();
				}
			}
		}
