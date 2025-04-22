	public final void testPartIdSubmission() throws PartInitException {
		final String testContextId = "org.eclipse.ui.tests.contexts.Bug74990";
		final IWorkbenchContextSupport contextSupport = fWorkbench
				.getContextSupport();
		final IContext testContext = contextSupport.getContextManager()
				.getContext(testContextId);
