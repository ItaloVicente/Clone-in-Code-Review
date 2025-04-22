	private static void unmap(GitProjectData data) {
		for (RepositoryMapping m : data.mappings.values()) {
			IContainer c = m.getContainer();
			if (c != null && c.isAccessible()) {
				try {
					c.setSessionProperty(MAPPING_KEY, null);
				} catch (CoreException e) {
					Activator.logWarning(MessageFormat.format(
							CoreText.GitProjectData_failedToUnmapRepoMapping,
							c.getFullPath()), e);
				}
			}
		}
	}

