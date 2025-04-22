	public static void refreshIfNeeded() {
		for (IWorkbenchWindow window : PlatformUI.getWorkbench()
				.getWorkbenchWindows())

			for (IWorkbenchPage page : window.getPages()) {
				IViewReference ref = page.findViewReference(VIEW_ID);
				if (ref != null) {
					IViewPart vp = ref.getView(false);
					if (vp != null) {
						RepositoriesView rv = (RepositoriesView) vp;
						rv.scheduleRefresh();
					}
				}
			}
	}

