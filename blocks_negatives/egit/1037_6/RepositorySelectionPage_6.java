	/**
	 * Gets the previously added URIs from the preferences
	 *
	 * TODO move this to some proper preferences handling class instead of
	 * making it static
	 *
	 * @return a (possibly empty) list of URIs, never <code>null</code>
	 */
	public static List<String> getUrisFromPrefs() {

		List<String> uriStrings = new ArrayList<String>();

		IEclipsePreferences prefs = new InstanceScope().getNode(Activator
				.getPluginId());
		String uriLengths = prefs.get(USED_URIS_LENGTH_PREF, ""); //$NON-NLS-1$
		String uris = prefs.get(USED_URIS_PREF, ""); //$NON-NLS-1$

		StringTokenizer tok = new StringTokenizer(uriLengths, " "); //$NON-NLS-1$
		int offset = 0;
		while (tok.hasMoreTokens()) {
			try {
				int length = Integer.parseInt(tok.nextToken());
				if (uris.length() >= (offset + length)) {
					uriStrings.add(uris.substring(offset, offset + length));
					offset += length;
				}
			} catch (NumberFormatException nfe) {
			}

		}

		return uriStrings;
	}

