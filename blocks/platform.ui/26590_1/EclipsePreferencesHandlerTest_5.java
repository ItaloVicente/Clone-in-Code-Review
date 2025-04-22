	public void testCustomizePreferenceOverriddenByCSS() throws Exception {
		IEclipsePreferences preferences = new EclipsePreferences();

		EclipsePreferencesHandlerTestable handler = new EclipsePreferencesHandlerTestable();

		handler.overridePropertyUnMocked(preferences, "name", "value");
		assertEquals("value", preferences.get("name", null));
		assertTrue(preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, "").contains(
				"name"));

		preferences.put("name", "customizedValue");

		assertEquals("customizedValue", preferences.get("name", null));
		assertFalse(preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, "").contains(
				"name"));
	}

