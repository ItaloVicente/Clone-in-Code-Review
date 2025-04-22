		if (isGTK2) {
			tracker = new Tracker(Display.getCurrent(), SWT.NULL);
		} else {
			tracker = new Tracker(Display.getCurrent().getActiveShell(), SWT.NULL);
		}

