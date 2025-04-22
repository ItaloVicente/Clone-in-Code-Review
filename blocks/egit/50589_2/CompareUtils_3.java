		RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		if (mapping == null) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					file.getLocation(), repository), null);
			return;
		}
		String path = mapping.getRepoRelativePath(
