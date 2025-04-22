		if (children == null) {
			List<GitModelObjectContainer> result = new ArrayList<GitModelObjectContainer>();
			Repository repo = gsd.getRepository();
			RevCommit srcRevCommit = gsd.getSrcRevCommit();
			RevCommit dstRevCommit = gsd.getDstRevCommit();
			List<Commit> commitCache;
			if (srcRevCommit != null && dstRevCommit != null)
				try {
					commitCache = CheckedInCommitsCache.build(repo, srcRevCommit,
							dstRevCommit);
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
					commitCache = null;
				}
			else
				commitCache = null;
			if (commitCache != null && !commitCache.isEmpty())
				result.addAll(getListOfCommit(commitCache));

			result.addAll(getWorkingChanges());

			children = result.toArray(new GitModelObjectContainer[result.size()]);
		}
