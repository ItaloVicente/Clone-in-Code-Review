	private void runAsync(Runnable action) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(() -> {
				if (!display.isDisposed() && PlatformUI.isWorkbenchRunning()) {
					action.run();
				}
			});
		}
	}

