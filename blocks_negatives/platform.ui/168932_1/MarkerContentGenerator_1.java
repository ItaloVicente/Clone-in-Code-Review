
		String legacyFilters = getLegacyFiltersPreferenceName();
		String migrationPreference = legacyFilters + MarkerSupportInternalUtilities.MIGRATE_PREFERENCE_CONSTANT;

		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(migrationPreference)) {
		}

		loadLegacyFiltersFrom(IDEWorkbenchPlugin.getDefault().getPreferenceStore().getString(legacyFilters));

		IDEWorkbenchPlugin.getDefault().getPreferenceStore().setValue(migrationPreference, true);
