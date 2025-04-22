
	protected String getPreviousPath(Repository repository,
			ObjectReader reader, RevCommit headCommit,
			RevCommit previousCommit, String path) throws IOException {
		TreeWalk walk = new TreeWalk(reader);
		walk.setRecursive(true);
		walk.addTree(previousCommit.getTree());
		walk.addTree(headCommit.getTree());

		List<DiffEntry> entries = DiffEntry.scan(walk);
		if (entries.size() < 2)
			return path;

		for (DiffEntry diff : entries)
			if (diff.getChangeType() == ChangeType.MODIFY
					&& path.equals(diff.getNewPath()))
				return path;

		RenameDetector detector = new RenameDetector(repository);
		detector.addAll(entries);
		List<DiffEntry> renames = detector.compute(walk.getObjectReader(),
				NullProgressMonitor.INSTANCE);
		for (DiffEntry diff : renames)
			if (diff.getChangeType() == ChangeType.RENAME
					&& path.equals(diff.getNewPath()))
				return diff.getOldPath();

		return path;
	}

	protected List<PreviousCommit> findPreviousCommits() throws IOException {
		List<PreviousCommit> result = new ArrayList<PreviousCommit>();
		Repository repository = getRepository();
		IResource resource = getSelectedResources()[0];
		String path = RepositoryMapping.getMapping(resource.getProject())
				.getRepoRelativePath(resource);
		RevWalk rw = new RevWalk(repository);
		try {
			if (path.length() > 0) {
				FollowFilter filter = FollowFilter.create(path);
				rw.setTreeFilter(filter);
			}

			RevCommit headCommit = rw.parseCommit(repository.getRef(
					Constants.HEAD).getObjectId());
			rw.markStart(headCommit);
			headCommit = rw.next();

			if (headCommit == null)
				return result;
			List<RevCommit> directParents = Arrays.asList(headCommit
					.getParents());

			RevCommit previousCommit = rw.next();
			while (previousCommit != null && result.size() < directParents.size()) {
				if (directParents.contains(previousCommit)) {
					String previousPath = getPreviousPath(repository,
							rw.getObjectReader(), headCommit, previousCommit,
							path);
					result.add(new PreviousCommit(previousCommit, previousPath));
				}
				previousCommit = rw.next();
			}
		} finally {
			rw.dispose();
		}
		return result;
	}

	protected static final class PreviousCommit {
		final RevCommit commit;
		final String path;
		PreviousCommit(final RevCommit commit, final String path) {
			this.commit = commit;
			this.path = path;
		}
	}

