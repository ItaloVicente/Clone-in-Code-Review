		ProgressInfoItem item = findProgressInfoItem(okJob);
		assertNotNull(item);
		item.executeTrigger();
		assertTrue(handler.executed);

		service.deactivateHandler(record);
	}

	public void testCommandPropertyEnablement() throws Exception {
		openProgressView();

		DummyJob okJob = new DummyJob("OK Job", Status.OK_STATUS);
		okJob.shouldFinish = false;

		IWorkbench workbench = PlatformUI.getWorkbench();
		ECommandService commandService = workbench.getService(ECommandService.class);
		String commandId = "org.eclipse.ui.tests.progressEnableViewCommand";
		Category category = commandService.defineCategory("org.eclipse.ui.tests.progress.category", "test", "test");
		Command command = commandService.defineCommand(commandId, "test", "test", category, new IParameter[0]);
		ParameterizedCommand parameterizedCommand = new ParameterizedCommand(command, null);
		okJob.setProperty(IProgressConstants2.COMMAND_PROPERTY, parameterizedCommand);
		okJob.setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
		okJob.schedule();

		ProgressInfoItem item = null;
		while ((item = findProgressInfoItem(okJob)) == null) {
			processEvents();
		}

		assertNotNull(item);
		assertFalse(item.isTriggerEnabled());

		IHandlerService service = workbench.getService(IHandlerService.class);
		CommandHandler handler = new CommandHandler();
		IHandlerActivation activation = service.activateHandler(commandId, handler);
		assertTrue(item.isTriggerEnabled());

		service.deactivateHandler(activation);
		assertFalse(item.isTriggerEnabled());

		okJob.cancel();
		okJob.join();
	}

	private ProgressInfoItem findProgressInfoItem(Job job) {
		for (ProgressInfoItem progressInfoItem : progressView.getViewer().getProgressInfoItems()) {
