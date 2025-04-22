	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		boolean mruControlledByCSS = preferences.getBoolean(MRU_CONTROLLED_BY_CSS_KEY, MRU_CONTROLLED_BY_CSS_DEFAULT);
		CSSPropertyMruVisibleSWTHandler.setMRUControlledByCSS(mruControlledByCSS);
	}

