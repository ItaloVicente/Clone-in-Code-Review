	public static void refreshIfNeeded() {
		for (IWorkbenchWindow window : PlatformUI.getWorkbench()
				.getWorkbenchWindows())
			for (IWorkbenchPage page : window.getPages())
				for (IViewReference vr : page.getViewReferences()) {
					if (vr.getId().equals(VIEW_ID)) {
						IViewPart vp = vr.getView(false);
						if (vp != null) {
							RepositoriesView rv = (RepositoriesView) vp;
							rv.scheduleRefresh();
						}

					}
				}
	}

