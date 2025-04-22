			backgroundWork.cancel();
			try {
				backgroundWork.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void runAsync(Runnable action) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null && !display.isDisposed()) {
			display.asyncExec(() -> {
				if (!display.isDisposed() && PlatformUI.isWorkbenchRunning()) {
					action.run();
				}
			});
