	}

	/**
	 * Stores back the known host names into the plugin's
	 * {@link IDialogSettings} if they were changed.
	 */
	public static void store() {
		if (modified) {
			String[] values = new String[knownHosts.size()];
			Activator.getDefault().getDialogSettings().put(KNOWN_HOSTS_KEY,
					knownHosts.keySet().toArray(values));
			modified = false;
		}
