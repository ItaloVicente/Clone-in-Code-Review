		protected void removeOverriddenPropertyNames(
				IEclipsePreferences preferences) {
			EclipsePreferencesHelper.removeOverriddenPropertyNames(preferences);
		}

		protected List<String> getOverriddenPropertyNames(
				IEclipsePreferences preferences) {
			return EclipsePreferencesHelper
					.getOverriddenPropertyNames(preferences);
		}

		protected Set<IEclipsePreferences> getPreferences() {
