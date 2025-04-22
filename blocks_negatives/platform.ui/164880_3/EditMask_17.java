	private DisposeListener disposeListener = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			text.removeVerifyListener(verifyListener);
			text.removeFocusListener(focusListener);
			text.removeDisposeListener(disposeListener);
		}
