		super.doSetUp();
		workbenchWindow = openTestWindow();
		activePage = workbenchWindow.getActivePage();
	}

	private void setupView() throws Throwable {
		view = activePage.showView("org.eclipse.ui.views.ResourceNavigator");
	}

	private void setupResources() throws Throwable {
		if (p1 == null) {
			p1 = FileUtil.createProject("TP1");
			f1 = null;
		}
		if (p2 == null) {
			p2 = FileUtil.createProject("TP2");
			f2 = null;
		}
		if (f1 == null) {
