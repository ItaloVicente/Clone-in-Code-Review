
		verify(preferences, times(1)).removePreferenceChangeListener(
				getPreferenceChangeListener());
	}

	public void testRemoveOverriddenByCssProperty() throws Exception {
		IEclipsePreferences preferences = new EclipsePreferences();

		appendOverriddenPropertyName(preferences, "prop1");
		appendOverriddenPropertyName(preferences, "prop2");
		appendOverriddenPropertyName(preferences, "prop3");

		removeOverriddenByCssProperty(preferences, "prop2");

		String overriddenPreferences = preferences.get(
				PROPS_OVERRIDDEN_BY_CSS_PROP, "");

		assertTrue(overriddenPreferences.contains(SEPARATOR + "prop1"
				+ SEPARATOR));
		assertFalse(overriddenPreferences.contains(SEPARATOR + "prop2"
				+ SEPARATOR));
		assertTrue(overriddenPreferences.contains(SEPARATOR + "prop3"
				+ SEPARATOR));
