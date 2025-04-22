	public void testUnchangedComplex() throws IOException {
		GitIndex index = new GitIndex(db);

		index.add(trash, writeTrashFile("a.b", "a.b"));
		index.add(trash, writeTrashFile("a.c", "a.c"));
		index.add(trash, writeTrashFile("a/b.b/b", "a/b.b/b"));
		index.add(trash, writeTrashFile("a/b", "a/b"));
		index.add(trash, writeTrashFile("a/c", "a/c"));
		index.add(trash, writeTrashFile("a=c", "a=c"));
		index.add(trash, writeTrashFile("a=d", "a=d"));
		index.write();
