	private ICompareInput getFileFromGit(GitSynchronizeData gsd, IPath location) {
		Repository repo = gsd.getRepository();
		File workTree = repo.getWorkTree();
		String repoRelativeLocation = Repository.stripWorkDir(workTree,
				location.toFile());

		TreeWalk tw = new TreeWalk(repo);
		tw.setRecursive(true);
		tw.setFilter(PathFilter.create(repoRelativeLocation.toString()));
		RevCommit baseCommit = gsd.getSrcRevCommit();
		RevCommit remoteCommit = gsd.getDstRevCommit();

		try {
			int baseNth = tw.addTree(baseCommit.getTree());
			int remoteNth = tw.addTree(remoteCommit.getTree());

			if (tw.next()) {
				ComparisonDataSource baseData = new ComparisonDataSource(
						baseCommit, tw.getObjectId(baseNth));
				ComparisonDataSource remoteData = new ComparisonDataSource(
						remoteCommit, tw.getObjectId(remoteNth));
				return new GitCompareInput(repo, baseData, baseData,
						remoteData, repoRelativeLocation);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return null;
	}

