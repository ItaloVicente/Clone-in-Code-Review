	public void release() {
		if (reader != null)
			reader.release();
	}

	public List<DiffEntry> scan(AnyObjectId a
			throws IOException {
		assertHaveRepository();

		RevWalk rw = new RevWalk(reader);
		return scan(rw.parseTree(a)
	}

	public List<DiffEntry> scan(RevTree a
		assertHaveRepository();

		CanonicalTreeParser aParser = new CanonicalTreeParser();
		CanonicalTreeParser bParser = new CanonicalTreeParser();

		aParser.reset(reader
		bParser.reset(reader

		return scan(aParser
	}

	public List<DiffEntry> scan(AbstractTreeIterator a
			throws IOException {
		assertHaveRepository();

		TreeWalk walk = new TreeWalk(reader);
		walk.reset();
		walk.addTree(a);
		walk.addTree(b);
		walk.setRecursive(true);

		if (pathFilter == TreeFilter.ALL) {
			walk.setFilter(TreeFilter.ANY_DIFF);
		} else if (pathFilter instanceof FollowFilter) {
			walk.setFilter(pathFilter);
		} else {
			walk.setFilter(AndTreeFilter
					.create(pathFilter
		}

		List<DiffEntry> files = DiffEntry.scan(walk);
		if (pathFilter instanceof FollowFilter && isAdd(files)) {
			a.reset();
			b.reset();
			walk.reset();
			walk.addTree(a);
			walk.addTree(b);
			walk.setFilter(TreeFilter.ANY_DIFF);

			if (renameDetector == null)
				setDetectRenames(true);
			files = updateFollowFilter(detectRenames(DiffEntry.scan(walk)));

		} else if (renameDetector != null)
			files = detectRenames(files);

		return files;
	}

	private List<DiffEntry> detectRenames(List<DiffEntry> files)
			throws IOException {
		renameDetector.reset();
		renameDetector.addAll(files);
		return renameDetector.compute(reader
	}

	private boolean isAdd(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (ent.getChangeType() == ADD && ent.getNewPath().equals(oldPath))
				return true;
		}
		return false;
	}

	private List<DiffEntry> updateFollowFilter(List<DiffEntry> files) {
		String oldPath = ((FollowFilter) pathFilter).getPath();
		for (DiffEntry ent : files) {
			if (isRename(ent) && ent.getNewPath().equals(oldPath)) {
				pathFilter = FollowFilter.create(ent.getOldPath());
				return Collections.singletonList(ent);
			}
		}
		return Collections.emptyList();
	}

	private static boolean isRename(DiffEntry ent) {
		return ent.getChangeType() == RENAME || ent.getChangeType() == COPY;
	}

	public void format(AnyObjectId a
		format(scan(a
	}

	public void format(RevTree a
		format(scan(a
	}

	public void format(AbstractTreeIterator a
			throws IOException {
		format(scan(a
	}

