	private void store(Repository db, ObjectId objId, TreeEntry treeEntry)
			throws IOException {
		String entry = treeEntry.getFullName();
		RevWalk walk = new RevWalk(db);
		walk.sort(RevSort.COMMIT_TIME_DESC, true);
		walk.sort(RevSort.BOUNDARY, true);
		walk.markStart(walk.parseCommit(objId));
		walk.setTreeFilter(AndTreeFilter.create(PathFilterGroup
				.createFromStrings(Collections.singleton(entry)),
				TreeFilter.ANY_DIFF));

		RevCommitList<RevCommit> list = new RevCommitList<RevCommit>();
		list.source(walk);

		int lastSize = 0;
		do {
			lastSize = list.size();
			list.fillTo(Integer.MAX_VALUE);
		} while (lastSize != list.size());

		dates.put(entry, list);
		updated.put(entry, treeEntry.getId());
