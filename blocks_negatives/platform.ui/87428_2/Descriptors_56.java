    private static DisposeListener disposeListener = new DisposeListener() {
        @Override
		public void widgetDisposed(DisposeEvent e) {
            doDispose(e.widget);
        }
    };
