	private void closeIntroView(IWorkbenchPage workbenchPage) {
		IViewPart intro = workbenchPage.findView("org.eclipse.ui.internal.introview");
		if (intro != null) {
			workbenchPage.hideView(intro);
		}
	}

