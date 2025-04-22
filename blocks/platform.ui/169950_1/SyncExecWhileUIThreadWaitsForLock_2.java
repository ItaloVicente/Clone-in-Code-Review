		activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		String viewId = "org.eclipse.pde.runtime.LogView";
		logView = (LogView) activePage.findView(viewId);
		if (logView == null) {
			shouldClose = true;
			logView = (LogView) activePage.showView(viewId);
		}
		UITestCase.processEvents();
		WorkbenchPlugin.log("Log for test: init log view");
		UITestCase.waitForJobs(100, 10000);
