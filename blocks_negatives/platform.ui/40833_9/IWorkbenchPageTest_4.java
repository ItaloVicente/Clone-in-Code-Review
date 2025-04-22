	/**
	 * Test opening a perspective with a fast view.
	 */
	public void XXXtestOpenPerspectiveWithFastView() {

		try {
			fWin.getWorkbench().showPerspective(
					PerspectiveWithFastView.PERSP_ID, fWin);
		} catch (WorkbenchException e) {
			fail("Unexpected WorkbenchException: " + e);
		}

		fail("facade.getFastViews() had no implementation");

		assertEquals(fActivePage.getViewReferences().length, 1);
		assertTrue(fActivePage.getViewReferences()[0].isFastView());

		IPerspectiveDescriptor persp = fActivePage.getPerspective();

		ICommandService commandService = fWorkbench.getService(ICommandService.class);
		Command command = commandService.getCommand("org.eclipse.ui.window.closePerspective");

		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(IWorkbenchCommandConstants.WINDOW_CLOSE_PERSPECTIVE_PARM_ID, persp.getId());

		ParameterizedCommand pCommand = ParameterizedCommand.generateCommand(command, parameters);

		IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		try {
			handlerService.executeCommand(pCommand, null);
		} catch (ExecutionException e1) {
		} catch (NotDefinedException e1) {
		} catch (NotEnabledException e1) {
		} catch (NotHandledException e1) {
		}

	}

