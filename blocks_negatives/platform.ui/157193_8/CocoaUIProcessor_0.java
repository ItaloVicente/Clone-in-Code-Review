		MCommand toggleFullscreenCommand = defineCommand("org.eclipse.ui.category.window", //$NON-NLS-1$
				"org.eclipse.ui.cocoa.fullscreenWindow", //$NON-NLS-1$
				"%command.fullscreen.name", "%command.fullscreen.desc", CONTRIBUTOR_URI); //$NON-NLS-1$//$NON-NLS-2$
		installHandler(toggleFullscreenCommand, FullscreenWindowHandler.class, CONTRIBUTOR_URI);
		installKeybinding("org.eclipse.ui.contexts.window", "COMMAND+CTRL+F", toggleFullscreenCommand); //$NON-NLS-1$ //$NON-NLS-2$

