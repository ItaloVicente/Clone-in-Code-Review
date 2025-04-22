		preferences
		.removePreferenceChangeListener(getPreferenceChangeListener());
	}

	public static void removeOverriddenByCssProperty(Preferences preferences,
			String preferenceToRemove) {
		StringBuilder overriddenByCSS = new StringBuilder(SEPARATOR);
		for (String preference : getOverriddenPropertyNames(preferences)) {
			if (!preference.equals(preferenceToRemove)) {
				overriddenByCSS.append(preference).append(SEPARATOR);
			}
		}
		preferences.put(PROPS_OVERRIDDEN_BY_CSS_PROP,
				overriddenByCSS.toString());
	}

	public static class PreferenceOverriddenByCssChangeListener implements
	IPreferenceChangeListener {
		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (isModified(event) && isRelatedToOverriddenByCss(event)) {
				removeOverriddenByCssProperty(event);
			}
		}

		private boolean isModified(PreferenceChangeEvent event) {
			return event.getOldValue() != null && event.getNewValue() != null;
		}

		private boolean isRelatedToOverriddenByCss(PreferenceChangeEvent event) {
			return isOverriddenByCSS(
					event.getNode()
					.get(PROPS_OVERRIDDEN_BY_CSS_PROP, SEPARATOR),
					event.getKey());
		}

		protected void removeOverriddenByCssProperty(PreferenceChangeEvent event) {
			EclipsePreferencesHelper.removeOverriddenByCssProperty(
					event.getNode(), event.getKey());
		}
