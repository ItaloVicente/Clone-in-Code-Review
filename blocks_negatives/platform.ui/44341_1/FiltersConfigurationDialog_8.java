		IPreferenceStore preferenceStore = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore();
		boolean useMarkerLimits = preferenceStore
				.getBoolean(IDEInternalPreferences.USE_MARKER_LIMITS);
		int markerLimits = useMarkerLimits ? preferenceStore
				.getInt(IDEInternalPreferences.MARKER_LIMITS_VALUE) : 100;
