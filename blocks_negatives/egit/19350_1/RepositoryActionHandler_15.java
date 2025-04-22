
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
