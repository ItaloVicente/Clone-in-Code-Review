		boolean activeShell = forceActive(window.getShell());

		final AtomicBoolean shellIsActive = new AtomicBoolean(activeShell);
		Assume.assumeTrue(shellIsActive.get());

		assertActiveWindow(window);

		ShellListener shellListener = new ShellStateListener(shellIsActive);
		window.getShell().addShellListener(shellListener);

