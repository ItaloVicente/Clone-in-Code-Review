		if (prefListener != null) {
			org.eclipse.egit.core.Activator.getDefault().getRepositoryUtil()
					.getPreferences()
					.removePreferenceChangeListener(prefListener);
			prefListener = null;
		}
