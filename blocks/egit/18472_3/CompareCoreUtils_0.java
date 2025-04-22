	public static DiffEntry getChangeDiffEntry(Repository repository, String newPath,
			RevCommit newCommit, RevCommit oldCommit, ObjectReader objectReader)
			throws IOException {
		TreeWalk walk = new TreeWalk(objectReader);
		walk.setRecursive(true);
		walk.addTree(oldCommit.getTree());
		walk.addTree(newCommit.getTree());

		List<DiffEntry> entries = DiffEntry.scan(walk);

		for (DiffEntry diff : entries) {
			if (diff.getChangeType() == ChangeType.MODIFY
					&& newPath.equals(diff.getNewPath()))
				return diff;
		}

		if (entries.size() < 2)
			return null;

		RenameDetector detector = new RenameDetector(repository);
		detector.addAll(entries);
		List<DiffEntry> renames = detector.compute(walk.getObjectReader(),
				NullProgressMonitor.INSTANCE);
		for (DiffEntry diff : renames) {
			if (diff.getChangeType() == ChangeType.RENAME
					&& newPath.equals(diff.getNewPath()))
				return diff;
		}

		return null;
	}
