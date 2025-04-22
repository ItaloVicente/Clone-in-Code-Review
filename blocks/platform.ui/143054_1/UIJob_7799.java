		Display asyncDisplay = getDisplay();
		if (asyncDisplay == null || asyncDisplay.isDisposed()) {
			return Status.CANCEL_STATUS;
		}
		asyncDisplay.asyncExec(() -> {
			IStatus result = null;
			Throwable throwable = null;
			try {
				setThread(Thread.currentThread());
				if (monitor.isCanceled()) {
