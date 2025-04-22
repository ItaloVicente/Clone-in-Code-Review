	private DisposeListener disposeListener = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			MockPart.this.widgetDisposed();
		}
	};
