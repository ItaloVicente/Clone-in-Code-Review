
	public static void processEvents() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			while (display.readAndDispatch()) {
			}
		}
	}
