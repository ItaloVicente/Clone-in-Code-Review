		if (!PlatformUI.isWorkbenchRunning())
			return false;
		final AtomicBoolean ret = new AtomicBoolean();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		if (display.isDisposed())
			return false;
		display.syncExec(new Runnable() {
			public void run() {
				ret.set(display.getActiveShell() != null);
			}
		});
		return ret.get();
