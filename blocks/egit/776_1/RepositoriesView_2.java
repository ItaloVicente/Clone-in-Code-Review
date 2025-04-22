	private final IPreferenceChangeListener configurationListener = new IPreferenceChangeListener() {

		public void preferenceChange(PreferenceChangeEvent event) {
			lastInputChange = System.currentTimeMillis();
		}
	};
