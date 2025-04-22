
	private void checkDisposed() {
		if (isDisposed) {
			throw new IllegalStateException("FormToolkit has been disposed"); //$NON-NLS-1$
		}
	}
