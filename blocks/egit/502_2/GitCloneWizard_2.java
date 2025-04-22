		final RepositoriesView view;
		IViewPart vp = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(RepositoriesView.VIEW_ID);
		if (vp != null) {
			view = (RepositoriesView) vp;
		} else {
			view = null;
		}

