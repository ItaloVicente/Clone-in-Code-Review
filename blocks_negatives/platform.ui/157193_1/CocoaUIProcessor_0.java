		MCommand toggleFullscreenCommand = defineCommand(
				"org.eclipse.ui.category.window", "org.eclipse.ui.cocoa.fullscreenWindow", //$NON-NLS-1$ //$NON-NLS-2$
				"%command.fullscreen.name", "%command.fullscreen.desc", CONTRIBUTOR_URI); //$NON-NLS-1$//$NON-NLS-2$
		installHandler(toggleFullscreenCommand, FullscreenWindowHandler.class,
				CONTRIBUTOR_URI);
		installKeybinding(
				"org.eclipse.ui.contexts.window", "COMMAND+CTRL+F", toggleFullscreenCommand); //$NON-NLS-1$ //$NON-NLS-2$

		installHandler(
				defineCommand(
						"org.eclipse.ui.category.window", "org.eclipse.ui.cocoa.zoomWindow", //$NON-NLS-1$ //$NON-NLS-2$
						"%command.zoom.name", "%command.zoom.desc", CONTRIBUTOR_URI), //$NON-NLS-1$//$NON-NLS-2$
