
	private void updateFollowFilter(ObjectId[] trees)
			throws MissingObjectException
			CorruptObjectException
		TreeWalk tw = pathFilter;
		FollowFilter oldFilter = (FollowFilter) tw.getFilter();
		tw.setFilter(TreeFilter.ANY_DIFF);
		tw.reset(trees);

		List<DiffEntry> files = DiffEntry.scan(tw);
		RenameDetector rd = new RenameDetector(repo);
		rd.addAll(files);
		files = rd.compute();

		TreeFilter newFilter = oldFilter;
		for (DiffEntry ent : files) {
			if (isRename(ent) && ent.getNewName().equals(oldFilter.getPath())) {
				newFilter = FollowFilter.create(ent.getOldName());
				break;
			}
		}
		tw.setFilter(newFilter);
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == ChangeType.RENAME
				|| ent.getChangeType() == ChangeType.COPY;
	}
