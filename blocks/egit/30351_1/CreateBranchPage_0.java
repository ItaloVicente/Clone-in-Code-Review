	private static void ensurePreferredHeight(Shell shell) {
		int preferredHeight = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		Point size = shell.getSize();
		if (size.y < preferredHeight)
			shell.setSize(size.x, preferredHeight);
	}

