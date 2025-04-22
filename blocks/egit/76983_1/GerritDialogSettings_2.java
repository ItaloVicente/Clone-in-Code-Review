		updateLastUri(repository, PUSH_TO_GERRIT_SECTION, config.getPushURIs());
	}

	private static void updateLastUri(@NonNull Repository repository,
			String sectionName, List<URIish> uris) {
		IDialogSettings section = getSection(sectionName);
