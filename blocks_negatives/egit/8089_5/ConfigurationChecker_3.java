		if (!hidden) {
			MessageDialogWithToggle dialog = MessageDialogWithToggle
					.openInformation(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(), title,
							message, toggleMessage, false, null, null);
			store.setValue(UIPreferences.SHOW_HOME_DIR_WARNING, !dialog
					.getToggleState());
		}
