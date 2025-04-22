		IPersistentPreferenceStore prefStore = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, IDEWorkbenchPlugin.IDE_WORKBENCH);
		boolean keepOnWarning = prefStore.getBoolean(keepOnWarningKey);
		if (keepOnWarning) {
			MessageDialogWithToggle dialog = new MessageDialogWithToggle(shell, title, null, message, severity,
					new String[] {IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL}, 0,
					IDEWorkbenchMessages.IDEApplication_version_doNotShowAgain, false);
			if (dialog.open() != Window.OK) {
				return false;
			}
			keepOnWarning = !dialog.getToggleState();
			try {
				prefStore.setValue(keepOnWarningKey, keepOnWarning);
				prefStore.save();
			} catch (IOException e) {
				IDEWorkbenchPlugin.log("Error writing to configuration preferences", //$NON-NLS-1$
					new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, e.getMessage(), e));
			}
		}
		return true;
	}
