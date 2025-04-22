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
