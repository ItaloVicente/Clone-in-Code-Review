		BundleContext context = FrameworkUtil.getBundle(ISystemSummarySection.class).getBundleContext();
		appendSection(SECTION_SYSTEM_PROPERTIES, WorkbenchMessages.SystemSummary_systemProperties, writer, context);
		appendSection(SECTION_SYSTEM_ENVIRONMENT, WorkbenchMessages.SystemSummary_systemVariables, writer, context);
		appendSection(SECTION_INSTALLED_FEATURES, WorkbenchMessages.SystemSummary_features, writer, context);
		appendSection(SECTION_INSTALLED_BUNDLES, WorkbenchMessages.SystemSummary_pluginRegistry, writer, context);
		appendSection(SECTION_USER_PREFERENCES, WorkbenchMessages.SystemSummary_userPreferences, writer, context);
