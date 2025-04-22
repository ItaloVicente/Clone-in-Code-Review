		IWorkbenchCommandSupport commandSupport = window.getWorkbench()
				.getCommandSupport();
		ICommandManager commandManager = commandSupport.getCommandManager();
		KeySequence expectedKeyBinding = KeySequence
				.getInstance("Ctrl+Shift+5"); //$NON-NLS-1$
		String commandId = commandManager.getPerfectMatch(expectedKeyBinding);
		assertEquals("org.eclipse.ui.tests.TestCommandId", commandId); //$NON-NLS-1$
	}
