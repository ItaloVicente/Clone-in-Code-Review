		RenameDetector detector = new RenameDetector(repository);
		detector.addAll(entries);
		List<DiffEntry> renames = detector.compute(walk.getObjectReader(),
				NullProgressMonitor.INSTANCE);
		for (DiffEntry diff : renames) {
			if (diff.getChangeType() == ChangeType.RENAME
					&& newPath.equals(diff.getNewPath()))
				return diff;
		}
