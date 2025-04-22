	public void testAddOnDisplayDisposed() throws Exception {
		final Listener listener = mock(Listener.class);

		Display display = mock(Display.class);

		new StylingPreferencesHandler(display) {
			@Override
			protected Listener createOnDisplayDisposedListener() {
				return listener;
