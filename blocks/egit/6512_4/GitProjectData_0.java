	private void logGoneMappedResource(final RepositoryMapping m) {
		Activator.logError(MessageFormat.format(
				CoreText.GitProjectData_mappedResourceGone, m.toString()),
				new FileNotFoundException(m.getContainerPath().toString()));
	}

