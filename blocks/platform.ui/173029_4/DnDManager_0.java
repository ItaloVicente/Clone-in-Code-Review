		Shell activeShell = Display.getCurrent().getActiveShell();
		if (activeShell == null) {
			return;
		}

		tracker = new Tracker(activeShell, SWT.NULL);
