	public static void hideView(final String viewId) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
				IViewReference[] views = workbenchPage.getViewReferences();
				for (int i = 0; i < views.length; i++) {
					IViewReference view = views[i];
					if (viewId.equals(view.getId())) {
						workbenchPage.hideView(view);
					}
				}
			}
		});
	}

