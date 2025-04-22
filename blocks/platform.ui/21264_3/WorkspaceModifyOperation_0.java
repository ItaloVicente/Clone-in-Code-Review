			if (monitor != null && PlatformUI.isWorkbenchRunning()
					&& !PlatformUI.getWorkbench().isStarting()) {
				Display display = PlatformUI.getWorkbench().getDisplay();
				if (!display.isDisposed()
						&& display.getThread() == Thread.currentThread()) {
					monitor = new EventLoopProgressMonitor(monitor);
				}
			}
