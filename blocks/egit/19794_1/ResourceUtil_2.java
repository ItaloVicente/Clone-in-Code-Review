			Repository repository = repositoryCache.getRepository(path);
			if (repository != null) {
				IPath repoPath = new Path(repository.getWorkTree()
						.getAbsolutePath());
				IPath repoRelativePath = path.makeRelativeTo(repoPath);
				addPathToMap(repository, repoRelativePath.toString(), result);
			}
