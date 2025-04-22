		shutDownRepositories();
		FileUtils.delete(repositoryFile.getParentFile(), FileUtils.RECURSIVE
				| FileUtils.RETRY);
		FileUtils.delete(remoteRepositoryFile, FileUtils.RECURSIVE
				| FileUtils.RETRY);
		FileUtils.delete(clonedRepositoryFile.getParentFile(),
				FileUtils.RECURSIVE | FileUtils.RETRY);
