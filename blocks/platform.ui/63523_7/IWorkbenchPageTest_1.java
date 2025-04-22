		boolean activeShell = forceActive(fWin.getShell());

		final AtomicBoolean shellIsActive = new AtomicBoolean(activeShell);
		Assume.assumeTrue(shellIsActive.get());

		ShellListener shellListener = new ShellStateListener(shellIsActive);
		fWin.getShell().addShellListener(shellListener);

