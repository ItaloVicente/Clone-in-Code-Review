	public synchronized Repository getSubmoduleRepository(IResource res) {
		IPath projectRelativePath = res.getProjectRelativePath();
		if (projectRelativePath == null)
			return null;

		String projectRelativePathStr = res.getProjectRelativePath().toString();
		try {
			if (SubmoduleWalk.containsGitModulesFile(db)) {
				SubmoduleWalk sw = SubmoduleWalk.forIndex(db);
				while (sw.next()) {
					if (projectRelativePathStr.startsWith(sw.getPath())) {
						Repository subRepo = sw.getRepository();
						if (subRepo == null)
							return null;

						Repository cachedRepo = null;
						try {
							cachedRepo = repositoryCache
									.lookupRepository(subRepo.getDirectory());
						} finally {
							subRepo.close();
						}
						return cachedRepo;
					}
				}
			}
		} catch (IOException e) {
			Activator.logWarning(
					CoreText.RepositoryMapping_ExceptionSubmoduleWalk, e);
		}
		return db;
	}

