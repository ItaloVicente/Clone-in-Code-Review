
		boolean hideable = toolControl.getTags().contains("HIDEABLE"); //$NON-NLS-1$
		boolean showRestoreMenu = toolControl.getTags().contains(
				"SHOW_RESTORE_MENU"); //$NON-NLS-1$
		if (showRestoreMenu || hideable) {
			createToolControlMenu(toolControl, newCtrl, hideable);
		}

