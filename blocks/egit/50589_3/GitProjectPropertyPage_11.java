		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (mapping != null) {
			Repository repository = mapping.getRepository();

			if (repository != null) {
				try {
					createHeadLink(repository, composite);
					fillValues(repository);
				} catch (IOException e) {
					if (GitTraceLocation.UI.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.UI.getLocation(),
								e.getMessage(), e);
				}
