		try {
			IProject project = resource.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null)
				return;
			String repoPath = mapping.getRepoRelativePath(resource);
			if( repoPath == null)
				return;
