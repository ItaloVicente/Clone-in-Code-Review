
	private IResourceVariant handleRepositoryRoot(final IResource resource,
			Repository repo, RevCommit revCommit) throws TeamException {
		try {
			return new GitFolderResourceVariant(repo, revCommit,
					revCommit.getTree(), resource.getLocation().toString());
		} catch (IOException e) {
			throw new TeamException(
					NLS.bind(
							CoreText.GitResourceVariantTree_couldNotFindResourceVariant,
							resource), e);
		}
	}

	private TreeWalk initializeTreeWalk(Repository repo, String path)
			throws CorruptObjectException {
		TreeWalk tw = new TreeWalk(repo);
		tw.reset();
		int ignoreNth = tw.addTree(new FileTreeIterator(repo));

		TreeFilter pathFilter = PathFilter.create(path);
		TreeFilter ignoreFilter = new NotIgnoredFilter(ignoreNth);
		tw.setFilter(AndTreeFilter.create(pathFilter, ignoreFilter));

		return tw;
	}

	private String getPath(final IResource resource, Repository repo) {
		return Repository.stripWorkDir(repo.getWorkTree(), resource
				.getLocation().toFile());
	}

