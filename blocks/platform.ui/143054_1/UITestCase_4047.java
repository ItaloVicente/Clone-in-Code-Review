		String name = runningTest != null ? runningTest : this.getName();
		trace(TestRunLogUtil.formatTestStartMessage(name));
		addWindowListener();
		doSetUp();

	}

	protected void doSetUp() throws Exception {
	}

	@After
	@Override
	public final void tearDown() throws Exception {
		String name = runningTest != null ? runningTest : this.getName();
		trace(TestRunLogUtil.formatTestFinishedMessage(name));
		removeWindowListener();
		doTearDown();
		fWorkbench = null;
	}

	protected void doTearDown() throws Exception {
		processEvents();
		closeAllTestWindows();
		processEvents();
	}
