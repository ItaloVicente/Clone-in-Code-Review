
	@SuppressWarnings("restriction")
	@Test
	public void testMacroIntegration() throws Exception {
		KeyBindingDispatcher dispatcher = new KeyBindingDispatcher();
		workbenchContext.set(KeyBindingDispatcher.class, dispatcher);
		ContextInjectionFactory.inject(dispatcher, workbenchContext);
		EMacroContext macroContext = workbenchContext.get(EMacroContext.class);
		macroContext.toggleMacroRecord();
		assertTrue(macroContext.isRecording());


		final Listener listener = dispatcher.getKeyDownFilter();
		display.addFilter(SWT.KeyDown, listener);
		display.addFilter(SWT.Traverse, listener);

		assertFalse(handler.q2);

		Shell shell = new Shell(display, SWT.NONE);

		HashMap<String, Boolean> macroAcceptedCommandIds = new HashMap<>();
		macroAcceptedCommandIds.put(TEST_ID1, true);
		setAcceptedMacroCommandIds(dispatcher, macroAcceptedCommandIds);
		notifyCtrlA(shell);

		assertTrue(handler.q2);

		macroContext.toggleMacroRecord();
		assertFalse(macroContext.isRecording());

		handler.q2 = false;
		macroContext.playbackLastMacro(new IMacroPlaybackContext() {
		});
		assertTrue(handler.q2);

		handler.q2 = false;
		setAcceptedMacroCommandIds(dispatcher, new HashMap<>());

		macroContext.toggleMacroRecord();
		assertTrue(macroContext.isRecording());
		notifyCtrlA(shell);
		macroContext.toggleMacroRecord();
		assertFalse(handler.q2);
	}

	@SuppressWarnings("restriction")
	private void setAcceptedMacroCommandIds(KeyBindingDispatcher dispatcher,
			HashMap<String, Boolean> macroAcceptedCommandIds) throws Exception {
		Field macroAcceptedCommandIdsField = dispatcher.getClass().getDeclaredField("fMacroAcceptedCommandIds");
		macroAcceptedCommandIdsField.setAccessible(true);
		macroAcceptedCommandIdsField.set(dispatcher, macroAcceptedCommandIds);
	}

	private void notifyCtrlA(Shell shell) {
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

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@SuppressWarnings("restriction")
	@Test
	public void testMacroIntegrationSaveRestore() throws Exception {
		File macrosDirectory = folder.getRoot();
		MacroManager macroManager = new MacroManager(macrosDirectory);
		try (Closeable closeable = MacroManager.withTemporaryDefaultInstance(macroManager)) {
			FilenameFilter macrosFilter = new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".js");
				}
			};
			assertEquals(0, macrosDirectory.list(macrosFilter).length);

			KeyBindingDispatcher dispatcher = new KeyBindingDispatcher();
			workbenchContext.set(KeyBindingDispatcher.class, dispatcher);
			ContextInjectionFactory.inject(dispatcher, workbenchContext);
			EMacroContext macroContext = workbenchContext.get(EMacroContext.class);

			final Listener listener = dispatcher.getKeyDownFilter();
			display.addFilter(SWT.KeyDown, listener);
			display.addFilter(SWT.Traverse, listener);

			assertFalse(handler.q2);

			Shell shell = new Shell(display, SWT.NONE);

			HashMap<String, Boolean> macroAcceptedCommandIds = new HashMap<>();
			macroAcceptedCommandIds.put(TEST_ID1, true);
			setAcceptedMacroCommandIds(dispatcher, macroAcceptedCommandIds);

			macroContext.toggleMacroRecord();
			notifyCtrlA(shell);
			assertTrue(handler.q2);
			macroContext.toggleMacroRecord();

			assertEquals(1, macrosDirectory.list(macrosFilter).length);

			macroManager.reloadMacros();
			handler.q2 = false;
			macroContext.playbackLastMacro(new IMacroPlaybackContext() {
			});
			assertTrue(handler.q2);
		}
	}


