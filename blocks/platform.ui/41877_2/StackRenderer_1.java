	@SuppressWarnings("restriction")
	private boolean getMruValue(Control control) {
		if (CSSPropertyMruVisibleSWTHandler.isMruControlledByCSS()) {
			return getInitialMRUValue(control);
		}
		return getMRUValueFromPreferences();
	}

	private boolean getMRUValueFromPreferences() {
		boolean initialMRUValue = preferences.getBoolean(MRU_KEY_DEFAULT, true);
		boolean actualValue = preferences.getBoolean(MRU_KEY, initialMRUValue);
		return actualValue;
	}

	private void updateMruValue(CTabFolder ctf) {
		boolean actualMRUValue = getMruValue(ctf);
		ctf.setMRUVisible(actualMRUValue);
	}

	@SuppressWarnings("restriction")
	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		boolean mruControlledByCSS = preferences.getBoolean(MRU_CONTROLLED_BY_CSS_KEY, MRU_CONTROLLED_BY_CSS_DEFAULT);
		CSSPropertyMruVisibleSWTHandler.setMruControlledByCSS(mruControlledByCSS);
	}

