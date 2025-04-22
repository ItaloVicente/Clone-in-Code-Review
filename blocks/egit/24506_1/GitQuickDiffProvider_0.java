
		if (document == null)
			document = createDocument(resource);
		return document;
	}

	private static GitDocument createDocument(IResource resource) {
		try {
			return GitDocument.create(resource);
		} catch (IOException e) {
			Activator.error(UIText.QuickDiff_failedLoading, e);
