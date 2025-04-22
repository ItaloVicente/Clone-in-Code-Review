	protected String getPreviousPath(Repository repository,
			ObjectReader reader, RevCommit headCommit,
			RevCommit previousCommit, String path) throws IOException {
		TreeWalk walk = new TreeWalk(reader);
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

