		IDialogSettings section = getSection(FETCH_FROM_GERRIT_SECTION);
		String configured = section.get(repository + LAST_URI_SUFFIX);
		if (configured == null || configured.isEmpty()) {
			List<URIish> fetchUris = config.getURIs();
			if (!fetchUris.isEmpty()) {
				section.put(repository + LAST_URI_SUFFIX,
						fetchUris.get(0).toPrivateString());
			}
		}
