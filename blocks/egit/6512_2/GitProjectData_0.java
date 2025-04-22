	private void logGoneMappedResource(final RepositoryMapping m) {
		String path = m.getContainerPath().toString();
		Activator.logError(MessageFormat.format(
				CoreText.GitProjectData_mappedResourceGone, path),
				new FileNotFoundException(path));
	}

