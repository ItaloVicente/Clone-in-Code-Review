		writer.println(WorkbenchMessages.SystemSummary_pluginRegistry);

		Bundle[] bundles = WorkbenchPlugin.getDefault().getBundles();
		AboutBundleData[] bundleInfos = new AboutBundleData[bundles.length];

		for (int i = 0; i < bundles.length; ++i) {
			bundleInfos[i] = new AboutBundleData(bundles[i]);
		}

		AboutData.sortById(false, bundleInfos);

		for (AboutBundleData info : bundleInfos) {
			String[] args = new String[] { info.getId(), info.getVersion(), info.getName(), info.getStateName() };
			writer.println(NLS.bind(WorkbenchMessages.SystemSummary_descriptorIdVersionState, args));
		}
	}

	/**
	 * Appends the preferences
	 */
	private void appendUserPreferences(PrintWriter writer) {
		IPreferencesService service = Platform.getPreferencesService();
		IEclipsePreferences node = service.getRootNode();
		ByteArrayOutputStream stm = new ByteArrayOutputStream();
