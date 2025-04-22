		RepositoryProvider provider = RepositoryProvider.getProvider(resource
				.getProject());
		if (provider != null) {
			try {
				document = GitDocument.create(resource);
			} catch (IOException e) {
				Activator.error(UIText.QuickDiff_failedLoading, e);
			}
			return document;
		} else {
