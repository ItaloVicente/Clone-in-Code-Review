		appendProperties(writer);
		appendEnvironmentVariables(writer);
		appendFeatures(writer);
		appendRegistry(writer);
		appendUserPreferences(writer);
	}

	/**
	 * Appends the <code>System</code> properties.
	 */
	private void appendProperties(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemProperties);
		Properties properties = System.getProperties();
		SortedSet<Object> set = new TreeSet<>((o1, o2) -> {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.compareTo(s2);
		});
		set.addAll(properties.keySet());
		Iterator<Object> i = set.iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = properties.getProperty(key);

			writer.print(key);
			writer.print('=');

			if (key.startsWith(ECLIPSE_PROPERTY_PREFIX)) {
				printEclipseProperty(writer, value);
				for (int j = 0; j < value.length(); j++) {
					writer.print('*');
				}
				writer.println();
			} else {
				writer.println(value);
			}
		}
	}

	private static void printEclipseProperty(PrintWriter writer, String value) {
		for (String line : lines) {
			writer.println(line);
		}
	}

	/**
	 * Appends the installed and configured features.
	 */
	private void appendFeatures(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_features);

		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		LinkedList<AboutBundleGroupData> groups = new LinkedList<>();
		if (providers != null) {
			for (IBundleGroupProvider provider : providers) {
				IBundleGroup[] bundleGroups = provider.getBundleGroups();
				for (IBundleGroup bundleGroup : bundleGroups) {
					groups.add(new AboutBundleGroupData(bundleGroup));
				}
			}
		}
		AboutBundleGroupData[] bundleGroupInfos = groups.toArray(new AboutBundleGroupData[0]);

		AboutData.sortById(false, bundleGroupInfos);

		for (AboutBundleGroupData info : bundleGroupInfos) {
			String[] args = new String[] { info.getId(), info.getVersion(), info.getName() };
			writer.println(NLS.bind(WorkbenchMessages.SystemSummary_featureVersion, args));
		}
