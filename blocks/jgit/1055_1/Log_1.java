
	private List<DiffEntry> detectRenames(List<DiffEntry> files)
			throws IOException {
		RenameDetector rd = new RenameDetector(db);
		if (renameLimit != null)
			rd.setRenameLimit(renameLimit.intValue());
		rd.addAll(files);
		return rd.compute();
	}

	private boolean isAdd(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (ent.getChangeType() == ChangeType.ADD
					&& ent.getNewName().equals(oldPath))
				return true;
		}
		return false;
	}

	private List<DiffEntry> updateFollowFilter(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (isRename(ent) && ent.getNewName().equals(oldPath)) {
				pathFilter = FollowFilter.create(ent.getOldName());
				return Collections.singletonList(ent);
			}
		}
		return Collections.emptyList();
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == ChangeType.RENAME
				|| ent.getChangeType() == ChangeType.COPY;
	}
