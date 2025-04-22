		assumeTrue("Testwindow not active, active shell: " + PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				forceActive(window.getShell()));
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		waitForJobs(200, 2000);
