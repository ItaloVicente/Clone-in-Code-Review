	public static void processUIEvents() {
		if (Display.getCurrent() != null) {
			while (Display.getCurrent().readAndDispatch()) {
			}
		} else {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					processUIEvents();
				}
			});
		}
	}

