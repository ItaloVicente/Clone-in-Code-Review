	private final DisposeListener disposeListener = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			if (e.widget == widget) {
				dispose();
				widget = null;
