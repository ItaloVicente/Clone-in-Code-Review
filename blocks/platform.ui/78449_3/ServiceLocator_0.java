	void checkUiThread() {
		if (Display.getCurrent() == null) {
			IllegalStateException e = new IllegalStateException(
					"Unexpected access to ServiceLocator from non-UI thread " + Thread.currentThread().getName()); //$NON-NLS-1$
			WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING, e.getMessage(), e));
		}
	}

