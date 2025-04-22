		RepositoryMapping repoMapping = RepositoryMapping.getMapping(resource);
		if (repoMapping == null) { // fix for bug 317368
			return null;
		}

		String gitPath = repoMapping.getRepoRelativePath(resource);
