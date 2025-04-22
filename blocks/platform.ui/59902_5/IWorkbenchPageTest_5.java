	public void testFindHistoryViewReferenceAfterShowViewCommand() throws Throwable {
		String historyView = "org.eclipse.team.ui.GenericHistoryView";
		fWin.getWorkbench().showPerspective(ViewPerspective.ID, fWin);
		assertNull(fActivePage.findView(MockViewPart.ID4));
		assertNull(fActivePage.findView(MockViewPart.ID2));
		assertNotNull(fActivePage.findView(MockViewPart.ID));

		assertNull(fActivePage.findViewReference(MockViewPart.ID4));
		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNull(fActivePage.findViewReference(historyView));
		assertNotNull(fActivePage.findViewReference(MockViewPart.ID));

		showViewViaCommand(historyView);
		assertNotNull(fActivePage.findView(historyView));
		assertNotNull(fActivePage.findViewReference(historyView));

		fWin.getWorkbench().showPerspective(SessionPerspective.ID, fWin);
		assertNull(fActivePage.findView(MockViewPart.ID2));
		assertNull(fActivePage.findView(MockViewPart.ID4));
		assertNull(fActivePage.findView(historyView));
		assertNotNull(fActivePage.findView(SessionView.VIEW_ID));

		assertNull(fActivePage.findViewReference(MockViewPart.ID2));
		assertNull(fActivePage.findViewReference(MockViewPart.ID4));
		assertNull(fActivePage.findViewReference(historyView));
		assertNotNull(fActivePage.findViewReference(SessionView.VIEW_ID));

		showViewViaCommand(historyView);
		assertNotNull(fActivePage.findView(historyView));
		assertNotNull(fActivePage.findViewReference(historyView));
	}

	private void showViewViaCommand(String viewId) throws Throwable {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(IWorkbenchCommandConstants.VIEWS_SHOW_VIEW_PARM_ID, viewId);

		Command command = createCommand(IWorkbenchCommandConstants.VIEWS_SHOW_VIEW);
		ExecutionEvent event = createEvent(command, parameters);
		command.executeWithChecks(event);
		while (fWorkbench.getDisplay().readAndDispatch()) {
		}
	}

	private ExecutionEvent createEvent(Command command, Map<String, String> parameters) {
		IWorkbench workbench = getWorkbench();
		IHandlerService handlerService = workbench.getService(IHandlerService.class);
		IEvaluationContext contextSnapshot = handlerService.createContextSnapshot(true);
		ExecutionEvent event = new ExecutionEvent(command, parameters, null, contextSnapshot);
		return event;
	}

	private Command createCommand(String id) {
		ICommandService commandService = getWorkbench().getService(ICommandService.class);
		return commandService.getCommand(id);
	}

