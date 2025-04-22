		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		assertNotNull(workbenchWindow);
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		assertNotNull(workbenchPage);
		workbenchPage.closeAllPerspectives(false, false);

		PlatformUI.getWorkbench().showPerspective(TestsPerspective.TESTS_PERSPECTIVE_ID, workbenchWindow);
