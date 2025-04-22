	public void testOnDisplayDisposedListener() throws Exception {
		StylingPreferencesHandlerTestable handler = spy(new StylingPreferencesHandlerTestable(mock(Display.class)));
		
		Listener listener = handler.createOnDisplayDisposedListener();
		
		listener.handleEvent(mock(org.eclipse.swt.widgets.Event.class));
		
		verify(handler, times(1)).resetOverriddenPreferences();
	}
	
