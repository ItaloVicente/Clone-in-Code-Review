		if (getSelection() != null) {
			if (!getSelection().equals(currentTheme)) {
				MessageDialog.openWarning(getShell(), WorkbenchMessages.ThemeChangeWarningTitle,
						WorkbenchMessages.ThemeChangeWarningText);
			}
			engine.setTheme(getSelection(), true);
