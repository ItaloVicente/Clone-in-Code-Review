		openViewMode = store.getDefaultInt(IPreferenceConstants.OPEN_VIEW_MODE);
		if (openViewMode == IPreferenceConstants.OVM_FLOAT) {
			openViewMode = IPreferenceConstants.OVM_FAST;
		}
		openEmbedButton
				.setSelection(openViewMode == IPreferenceConstants.OVM_EMBED);

