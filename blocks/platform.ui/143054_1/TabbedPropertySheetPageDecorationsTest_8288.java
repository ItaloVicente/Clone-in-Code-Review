		throws Exception {
		super.setUp();

		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow();
		assertNotNull(workbenchWindow);
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		assertNotNull(workbenchPage);
