		RepositoryMapping mapping = RepositoryMapping.getMapping(location);
		if (mapping == null) {
			return false;
		}
		String resRelPath = mapping.getRepoRelativePath(location);

