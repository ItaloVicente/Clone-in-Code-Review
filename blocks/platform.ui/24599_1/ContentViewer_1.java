	protected void handleDispose(DisposeEvent event) {
		RuntimeException ex = null;
		if (contentProvider != null) {
			try {
				contentProvider.inputChanged(this, getInput(), null);
			} catch (RuntimeException e) {
				ex = e;
			}
		}
		contentProvider.dispose();
		contentProvider = null;
		if (labelProvider != null) {
			labelProvider.removeListener(labelProviderListener);
			labelProvider.dispose();
			labelProvider = null;
		}
		input = null;
		if (ex != null) {
			throw ex;
		}
	}
