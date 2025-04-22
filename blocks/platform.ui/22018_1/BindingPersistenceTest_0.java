	public void testAboutBinding() throws Exception {
		ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		final Scheme activeScheme = bindingService.getActiveScheme();
		final Binding[] originalBindings = bindingService.getBindings();

		ParameterizedCommand aboutCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.HELP_ABOUT),
				null);
		ParameterizedCommand activateEditorCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.WINDOW_ACTIVATE_EDITOR),
				null);

		final KeySequence keyF12 = KeySequence.getInstance("F12");
		final KeySequence keyAltCtrlShiftI = KeySequence
				.getInstance("ALT+CTRL+SHIFT+I");
		final Binding editorBinding = bindingService.getPerfectMatch(keyF12);
		assertNotNull(editorBinding);
		assertEquals(activateEditorCmd, editorBinding.getParameterizedCommand());

		EBindingService ebs = (EBindingService) fWorkbench
				.getService(EBindingService.class);
		HashMap<String, String> attrs = new HashMap<String, String>();
		attrs.put(EBindingService.TYPE_ATTR_TAG, "user");
		final Binding localAboutBinding = ebs.createBinding(keyF12, aboutCmd,
				IContextService.CONTEXT_ID_WINDOW, attrs);
		assertEquals(Binding.USER, localAboutBinding.getType());

		final Binding[] bindings = originalBindings;
		Binding[] added = new Binding[bindings.length + 2];
		System.arraycopy(bindings, 0, added, 0, bindings.length);

		Binding del = new KeyBinding(keyF12, null,
				IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID,
				IContextService.CONTEXT_ID_WINDOW, null, null, null,
				Binding.USER);
		added[bindings.length] = del;
		added[bindings.length + 1] = localAboutBinding;
		bindingService.savePreferences(activeScheme, added);

		final Binding secondMatch = bindingService.getPerfectMatch(keyF12);
		assertNotNull(secondMatch);
		assertEquals(aboutCmd, secondMatch.getParameterizedCommand());

		bindingService.savePreferences(activeScheme, originalBindings);
		final Binding thirdMatch = bindingService.getPerfectMatch(keyF12);
		assertNotNull(thirdMatch);
		assertEquals(activateEditorCmd, thirdMatch.getParameterizedCommand());

		final Binding localAboutBinding1 = ebs.createBinding(keyAltCtrlShiftI,
				aboutCmd, IContextService.CONTEXT_ID_WINDOW, attrs);
		assertEquals(Binding.USER, localAboutBinding1.getType());
		Binding[] added1 = new Binding[bindings.length + 1];
		System.arraycopy(bindings, 0, added1, 0, bindings.length);
		added1[bindings.length] = localAboutBinding1;

		bindingService.savePreferences(activeScheme, added1);
		final Binding fourthMatch = bindingService
				.getPerfectMatch(keyAltCtrlShiftI);
		assertNotNull(fourthMatch);
		assertEquals(aboutCmd, fourthMatch.getParameterizedCommand());
	}

	public void testAboutBindingIn3x() throws Exception {
		ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		final Scheme activeScheme = bindingService.getActiveScheme();

		ParameterizedCommand aboutCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.HELP_ABOUT),
				null);
		ParameterizedCommand activateEditorCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.WINDOW_ACTIVATE_EDITOR),
				null);

		final KeySequence keyF12 = KeySequence.getInstance("F12");
		final Binding editorBinding = bindingService.getPerfectMatch(keyF12);
		assertNotNull(editorBinding);
		assertEquals(activateEditorCmd, editorBinding.getParameterizedCommand());

		EBindingService ebs = (EBindingService) fWorkbench
				.getService(EBindingService.class);
		HashMap<String, String> attrs = new HashMap<String, String>();
		attrs.put(EBindingService.TYPE_ATTR_TAG, "user");
		final Binding localAboutBinding = ebs.createBinding(keyF12, aboutCmd,
				IContextService.CONTEXT_ID_WINDOW, attrs);
		assertEquals(Binding.USER, localAboutBinding.getType());

		final Binding[] bindings = bindingService.getBindings();
		Binding[] added = new Binding[bindings.length + 1];
		System.arraycopy(bindings, 0, added, 0, bindings.length);

		added[bindings.length] = localAboutBinding;
		bindingService.savePreferences(activeScheme, added);

		final Binding secondMatch = bindingService.getPerfectMatch(keyF12);
		assertNotNull(secondMatch);
		assertEquals(aboutCmd, secondMatch.getParameterizedCommand());
	}

	public void testAboutBindingEmacs() throws Exception {

		ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		final Scheme emacsScheme = bindingService.getScheme(EMACS_SCHEME_ID);
		assertNotNull(emacsScheme);
		final Binding[] originalBindings = bindingService.getBindings();
		bindingService.savePreferences(emacsScheme, originalBindings);

		ParameterizedCommand findAndReplaceCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE),
				null);
		ParameterizedCommand aboutCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.HELP_ABOUT),
				null);

		final KeySequence keyAltR = KeySequence.getInstance("ALT+R");
		final KeySequence keyAltCtrlShiftI = KeySequence
				.getInstance("ALT+CTRL+SHIFT+I");
		final Binding findAndReplaceBinding = bindingService
				.getPerfectMatch(keyAltR);

		assertNotNull(findAndReplaceBinding);
		assertEquals(findAndReplaceCmd,
				findAndReplaceBinding.getParameterizedCommand());
		assertEquals(EMACS_SCHEME_ID, findAndReplaceBinding.getSchemeId());

		EBindingService ebs = (EBindingService) fWorkbench
				.getService(EBindingService.class);
		HashMap<String, String> attrs = new HashMap<String, String>();
		attrs.put(EBindingService.TYPE_ATTR_TAG, "user");
		attrs.put(EBindingService.SCHEME_ID_ATTR_TAG, EMACS_SCHEME_ID);
		final Binding localAboutBinding = ebs.createBinding(keyAltR, aboutCmd,
				IContextService.CONTEXT_ID_WINDOW, attrs);
		assertNotNull(localAboutBinding);
		assertEquals(Binding.USER, localAboutBinding.getType());
		assertEquals(EMACS_SCHEME_ID, localAboutBinding.getSchemeId());

		final Binding[] bindings = originalBindings;
		Binding[] added = new Binding[bindings.length + 2];
		System.arraycopy(bindings, 0, added, 0, bindings.length);

		Binding del = new KeyBinding(keyAltR, null, EMACS_SCHEME_ID,
				IContextService.CONTEXT_ID_WINDOW, null, null, null,
				Binding.USER);
		added[bindings.length] = del;
		added[bindings.length + 1] = localAboutBinding;
		bindingService.savePreferences(emacsScheme, added);

		final Binding secondMatch = bindingService.getPerfectMatch(keyAltR);
		assertNotNull(secondMatch);
		assertEquals(aboutCmd, secondMatch.getParameterizedCommand());

		bindingService.savePreferences(emacsScheme, originalBindings);
		final Binding thirdMatch = bindingService.getPerfectMatch(keyAltR);
		assertNotNull(thirdMatch);
		assertEquals(findAndReplaceCmd, thirdMatch.getParameterizedCommand());

		final Binding localAboutBinding1 = ebs.createBinding(keyAltCtrlShiftI,
				aboutCmd, IContextService.CONTEXT_ID_WINDOW, attrs);
		assertNotNull(localAboutBinding1);
		assertEquals(Binding.USER, localAboutBinding1.getType());
		assertEquals(EMACS_SCHEME_ID, localAboutBinding.getSchemeId());

		Binding[] added1 = new Binding[bindings.length + 1];
		System.arraycopy(bindings, 0, added1, 0, bindings.length);
		added1[bindings.length] = localAboutBinding1;

		bindingService.savePreferences(emacsScheme, added1);
		final Binding fourthMatch = bindingService
				.getPerfectMatch(keyAltCtrlShiftI);
		assertNotNull(fourthMatch);
		assertEquals(aboutCmd, fourthMatch.getParameterizedCommand());
		assertEquals(EMACS_SCHEME_ID, fourthMatch.getSchemeId());
	}

	public void testPasteAndRedoBindingEmacs() throws Exception {
		ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		final Scheme emacsScheme = bindingService.getScheme(EMACS_SCHEME_ID);
		assertNotNull(emacsScheme);
		final Scheme defaultScheme = bindingService
				.getScheme(IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID);
		assertNotNull(defaultScheme);

		final Binding[] originalBindings = bindingService.getBindings();
		bindingService.savePreferences(emacsScheme, originalBindings);

		ParameterizedCommand pasteCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.EDIT_PASTE),
				null);
		ParameterizedCommand redoCmd = new ParameterizedCommand(
				commandService.getCommand(IWorkbenchCommandConstants.EDIT_REDO),
				null);

		final KeySequence keyCtrlY = KeySequence.getInstance("CTRL+Y");

		final Binding pasteBinding = bindingService.getPerfectMatch(keyCtrlY);
		assertNotNull(pasteBinding);
		assertEquals(pasteCmd, pasteBinding.getParameterizedCommand());
		assertEquals(EMACS_SCHEME_ID, pasteBinding.getSchemeId());

		bindingService.savePreferences(defaultScheme, originalBindings);
		final Binding redoBinding = bindingService.getPerfectMatch(keyCtrlY);
		assertNotNull(redoBinding);
		assertEquals(redoCmd, redoBinding.getParameterizedCommand());
		assertEquals(IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID,
				redoBinding.getSchemeId());
	}

	public void testPasteBindingEmacs() throws Exception {
		ICommandService commandService = (ICommandService) fWorkbench
				.getAdapter(ICommandService.class);
		IBindingService bindingService = (IBindingService) fWorkbench
				.getAdapter(IBindingService.class);

		final Scheme emacsScheme = bindingService.getScheme(EMACS_SCHEME_ID);
		assertNotNull(emacsScheme);
		final Binding[] originalBindings = bindingService.getBindings();
		bindingService.savePreferences(emacsScheme, originalBindings);

		ParameterizedCommand pasteCmd = new ParameterizedCommand(
				commandService
						.getCommand(IWorkbenchCommandConstants.EDIT_PASTE),
				null);

		final KeySequence keyCtrlY = KeySequence.getInstance("CTRL+Y");

		final Binding pasteBinding = bindingService.getPerfectMatch(keyCtrlY);
		assertNotNull(pasteBinding);
		assertEquals(pasteCmd, pasteBinding.getParameterizedCommand());
		assertEquals(EMACS_SCHEME_ID, pasteBinding.getSchemeId());
	}

