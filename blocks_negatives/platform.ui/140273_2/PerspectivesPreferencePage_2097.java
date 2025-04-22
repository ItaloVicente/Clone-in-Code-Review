		openPerspMode = store
				.getDefaultInt(IPreferenceConstants.OPEN_PERSP_MODE);
		openSameWindowButton
				.setSelection(IPreferenceConstants.OPM_ACTIVE_PAGE == openPerspMode);
		openNewWindowButton
				.setSelection(IPreferenceConstants.OPM_NEW_WINDOW == openPerspMode);
