
	private IProject prepareTestBug417255() throws Exception {
		blankLabelProviderOverride(BOTH, BLANK, "");

		_contentService.bindExtensions(new String[] { COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService().activateExtensions(new String[] { COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		IProject p1Project = ResourcesPlugin.getWorkspace().getRoot().getProject(TestWorkspace.P1_PROJECT_NAME);
		p1Project.setSessionProperty(Bug417255Decorator.DECO_PROP, DECORATION_TEXT_1);

		IDecoratorManager manager = PlatformUI.getWorkbench().getDecoratorManager();
		manager.setEnabled("org.eclipse.ui.tests.navigator.bug417255Decorator", true);

		waitForDecoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR); // wait for decorator to run
		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB); // wait for update job following decoration to

		TreeItem[] rootItems = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_1, rootItems[0].getText());

		return p1Project;
	}

	@Test
	public void testBug417255raceConditionDuringDecoration() throws Exception {
		IProject p1Project = prepareTestBug417255();

		p1Project.setSessionProperty(Bug417255Decorator.DECO_PROP, DECORATION_TEXT_2);

		Bug417255Decorator.blockDecoration();

		_viewer.update(p1Project, null);

		waitForDecoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);


		p1Project.setSessionProperty(Bug417255Decorator.DECO_PROP, DECORATION_TEXT_3);
		_viewer.update(p1Project, null); // this should cause another decoration request to be scheduled

		Bug417255Decorator.unblockDecoration();

		waitForDecoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);
		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB);

		TreeItem[] rootItemsAfter = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_3, rootItemsAfter[0].getText());
	}

	@Test
	public void testBug417255raceConditionBeforeUpdate() throws Exception {
		IProject p1Project = prepareTestBug417255();

		Semaphore updateJobScheduled = new Semaphore(0);
		IJobChangeListener listener = new IJobChangeListener() {
			@Override
			public void sleeping(IJobChangeEvent event) {
			}

			@Override
			public void scheduled(IJobChangeEvent event) {
				event.getJob().getName().equals(WorkbenchMessages.DecorationScheduler_UpdateJobName);
				updateJobScheduled.release();
			}

			@Override
			public void running(IJobChangeEvent event) {
			}

			@Override
			public void done(IJobChangeEvent event) {
			}

			@Override
			public void awake(IJobChangeEvent event) {
			}

			@Override
			public void aboutToRun(IJobChangeEvent event) {
			}
		};
		Job.getJobManager().addJobChangeListener(listener);

		p1Project.setSessionProperty(Bug417255Decorator.DECO_PROP, DECORATION_TEXT_2);
		_viewer.update(p1Project, null);

		waitForDecoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR);

		updateJobScheduled.acquire();

		p1Project.setSessionProperty(Bug417255Decorator.DECO_PROP, DECORATION_TEXT_3);
		_viewer.update(p1Project, null); // this *should* cause another decoration request to be scheduled

		Job.getJobManager().removeJobChangeListener(listener);

		waitForDecoration.waitForCondition(Display.getCurrent(), TIMEOUT_DECORATOR); // wait for decorator to run

		DisplayHelper.sleep(Display.getCurrent(), TIMEOUT_UPDATE_JOB);

		TreeItem[] rootItemsAfter = _viewer.getTree().getItems();
		assertEquals(TestWorkspace.P1_PROJECT_NAME + DECORATION_TEXT_3, rootItemsAfter[0].getText());
	}
