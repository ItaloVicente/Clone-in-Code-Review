	private static class KeyBindingInterceptor implements IKeyBindingInterceptor {

		private boolean interceptCommand = true;
		StringBuilder buffer = new StringBuilder();

		@Override
		public void postExecuteCommand(ParameterizedCommand parameterizedCommand, Event trigger, boolean commandDefined,
				boolean commandHandled) {
			buffer.append("postExecute:" + parameterizedCommand.getId() + "\n");
		}

		@Override
		public boolean executeCommand(ParameterizedCommand parameterizedCommand, Event event) {
			buffer.append("executeCommand:" + parameterizedCommand.getId() + "\n");
			if (interceptCommand) {
				return true;
			}
			return false;
		}
	}

	@Test
	public void testInterceptor() {
		KeyBindingDispatcher dispatcher = new KeyBindingDispatcher();
		ContextInjectionFactory.inject(dispatcher, workbenchContext);
		final Listener listener = dispatcher.getKeyDownFilter();
		display.addFilter(SWT.KeyDown, listener);
		display.addFilter(SWT.Traverse, listener);

		KeyBindingInterceptor interceptor = new KeyBindingInterceptor();
		dispatcher.addInterceptor(interceptor);

		assertFalse(handler.q2);

		Shell shell = new Shell(display, SWT.NONE);

		notifyKeyDownKeyUp(shell);
		assertFalse(handler.q2);

		interceptor.interceptCommand = false;
		notifyKeyDownKeyUp(shell);
		assertTrue(handler.q2);

		assertEquals("executeCommand:test.id1\nexecuteCommand:test.id1\npostExecute:test.id1\n",
				interceptor.buffer.toString());
	}

	private void notifyKeyDownKeyUp(Shell shell) {
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.keyCode = SWT.CTRL;
		shell.notifyListeners(SWT.KeyDown, event);

		event = new Event();
		event.type = SWT.KeyDown;
		event.stateMask = SWT.CTRL;
		event.keyCode = 'A';
		shell.notifyListeners(SWT.KeyDown, event);
	}

