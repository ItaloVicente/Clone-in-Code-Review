	}

	private List<String> getConfiguredKeys() {

		List<String> result = new ArrayList<String>();
		ScopedPreferenceStore store = new ScopedPreferenceStore(
				new InstanceScope(), Activator.getPluginId());
		String keys = store.getString(PREFERENCE_KEYS);
		if (keys.length() > 0) {
			StringTokenizer tok = new StringTokenizer(keys, " "); //$NON-NLS-1$
			while (tok.hasMoreTokens()) {
				result.add(tok.nextToken());
			}
		} else {
			result.addAll(ConfigureKeysDialog.standardKeys);
