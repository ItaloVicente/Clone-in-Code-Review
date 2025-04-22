		boolean activeShell = forceActive(window.getShell());

		waitForJobs(500, 5000);

		final AtomicBoolean shellIsActive = new AtomicBoolean(activeShell);
		Assume.assumeTrue(shellIsActive.get());

