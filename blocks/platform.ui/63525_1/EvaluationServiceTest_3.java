		waitForJobs(500, 3000);
		forceActiveShell(window.getShell());

		final AtomicBoolean shellIsActive = new AtomicBoolean(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow() == window);
		Assume.assumeTrue(shellIsActive.get());

		ShellListener shellListener = new MyShellListener(shellIsActive);
		window.getShell().addShellListener(shellListener);

