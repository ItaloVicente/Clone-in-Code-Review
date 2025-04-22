		Display display;
		if (PlatformUI.isWorkbenchRunning()
				&& !PlatformUI.getWorkbench().isStarting()) {
			display = PlatformUI.getWorkbench().getDisplay();
			if (!display.isDisposed()
					&& (display.getThread() == Thread.currentThread())) {
				return new EventLoopProgressMonitor(new NullProgressMonitor());
			}
