	static Point computeMinimumSize(Composite toCompute) {
		if (toCompute instanceof Shell) {
			if (minimumShellSize == null) {
				Shell testShell = new Shell((Shell) toCompute, SWT.DIALOG_TRIM | SWT.RESIZE);
				testShell.setSize(0, 0);
				minimumShellSize = testShell.getSize();
				testShell.dispose();
			}
