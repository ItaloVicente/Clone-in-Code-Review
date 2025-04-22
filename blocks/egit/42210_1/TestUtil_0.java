	public static void processUIEvents() {
		if (Display.getCurrent() != null) {
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch()) {
			}
		}
	}

