		openFastButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_FAST);

		if (isFVBConfigured)
			fvbHideButton.setSelection(store
					.getDefaultBoolean(IPreferenceConstants.FVB_HIDE));
