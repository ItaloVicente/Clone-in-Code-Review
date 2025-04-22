	private IDisposeListener disposeListener = new IDisposeListener() {
		@Override
		public void handleDispose(DisposeEvent staleEvent) {
			dispose();
		}
	};
