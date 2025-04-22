		toolbarManager.add(toggleTreeModeAction);
	}

	private void updateOutlineTreeMode(boolean doToggle, Action toggleAction) {
		IPreferenceStore preference = Activator.getDefault()
				.getPreferenceStore();
		boolean compact = preference.getBoolean(prefID);
		if (doToggle) {
			compact = !compact;
		}
