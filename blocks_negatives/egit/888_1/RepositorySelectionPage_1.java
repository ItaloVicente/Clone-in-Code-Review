	public static void saveUriInPrefs(String stringToAdd) {

		List<String> uriStrings = getUrisFromPrefs();

		if (uriStrings.indexOf(stringToAdd) == 0)
			return;
		uriStrings.add(0, stringToAdd);

		IEclipsePreferences prefs = new InstanceScope().getNode(Activator
				.getPluginId());

		StringBuilder sb = new StringBuilder();
		StringBuilder lb = new StringBuilder();

		for (String uriString : uriStrings) {
			sb.append(uriString);
			lb.append(uriString.length());
		}
		prefs.put(USED_URIS_PREF, sb.toString());
		prefs.put(USED_URIS_LENGTH_PREF, lb.toString());

		try {
			prefs.flush();
		} catch (BackingStoreException e) {
		}
