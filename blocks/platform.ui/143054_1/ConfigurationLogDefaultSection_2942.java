	}

	private void appendFeatures(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_features);

		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		LinkedList<AboutBundleGroupData> groups = new LinkedList<>();
		if (providers != null) {
