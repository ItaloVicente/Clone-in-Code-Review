		if (fHelpButton != null && fHelpButton.getSelection()) {
			DialogTray tray = getTray();
			if (tray != null) {
				closeTray();
			}
		}
		if (tray == null ||
