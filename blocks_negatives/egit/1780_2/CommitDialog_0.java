
		try {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(file.getProject());

			Repository repo = repositoryMapping.getRepository();
			GitIndex index = repo.getIndex();
			Tree headTree = repo.mapTree(Constants.HEAD);

			String repoPath = repositoryMapping.getRepoRelativePath(file);
			TreeEntry headEntry = (headTree == null ? null : headTree.findBlobMember(repoPath));
			boolean headExists = (headTree == null ? false : headTree.existsBlob(repoPath));

			Entry indexEntry = index.getEntry(repoPath);
			if (headEntry == null) {
