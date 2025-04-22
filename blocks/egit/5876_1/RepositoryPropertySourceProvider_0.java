	private void registerDisposal() {
		if (disposeListener != null)
			return;

		final Control control = myPage.getControl();
		if (control == null)
			return;

		disposeListener = new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				removeListener();
			}
		};
		control.addDisposeListener(disposeListener);
	}

	private void removeListener() {
		final ListenerHandle handle = listenerHandle;
		if (handle != null)
			handle.remove();
	}

