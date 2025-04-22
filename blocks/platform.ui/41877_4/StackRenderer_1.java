	private boolean getMRUValue(Control control) {
		if (CSSPropertyMruVisibleSWTHandler.isMRUControlledByCSS()) {
			return getInitialMRUValue(control);
		}
		return getMRUValueFromPreferences();
	}

	private boolean getMRUValueFromPreferences() {
		boolean initialMRUValue = preferences.getBoolean(MRU_KEY_DEFAULT, true);
		boolean actualValue = preferences.getBoolean(MRU_KEY, initialMRUValue);
		return actualValue;
	}

	private void updateMRUValue(CTabFolder ctf) {
		boolean actualMRUValue = getMRUValue(ctf);
		ctf.setMRUVisible(actualMRUValue);
	}

	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		boolean mruControlledByCSS = preferences.getBoolean(MRU_CONTROLLED_BY_CSS_KEY, MRU_CONTROLLED_BY_CSS_DEFAULT);
		CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSS);
	}

