	abstract ObjectId getRevObjId(IResource resource) throws IOException;

	/**
	 * Initializes the repository information for the specified resource.
	 *
	 * @param resource
	 *            the resource that needs to have its repository information
	 *            initialized for
	 * @throws IOException
	 *             if an error occurs while walking the branch
	 */
	private synchronized void initialize(IResource resource) throws IOException {
		IProject project = resource.getProject();
		if (!gsdData.contains(project)) {
			return;
		}

		Repository db = gsdData.getData(project).getRepository();
		if (!trees.containsKey(db)) {
			Tree tree = getRevTree(resource);
			ObjectId objId = getRevObjId(resource);

			if (objId != null && tree != null) {
				trees.put(db, tree);
				walk(db, objId, tree);
			}
		}
	}

	private void walk(final Repository db, final ObjectId objId, Tree merge)
			throws IOException {
		IndexTreeWalker walker = new IndexTreeWalker(db.getIndex(), merge, db
				.getWorkDir(), new AbstractIndexTreeVisitor() {
			public void visitEntry(TreeEntry treeEntry, Entry indexEntry,
					File file) throws IOException {
				if (treeEntry != null && contains(file)) {
					store(db, objId, treeEntry);
				}
			}
		});
		walker.walk();
	}

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
