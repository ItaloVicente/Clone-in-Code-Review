		verify(display, times(1)).addListener(SWT.Dispose, listener);
	}

	public void testOnDisplayDisposedListener() throws Exception {
		StylingPreferencesHandlerTestable handler = spy(new StylingPreferencesHandlerTestable(mock(Display.class)));

		Listener listener = handler.createOnDisplayDisposedListener();

		listener.handleEvent(mock(org.eclipse.swt.widgets.Event.class));
