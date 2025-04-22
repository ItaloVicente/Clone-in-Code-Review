	public void testUnchangedComplex() throws IOException
			NoFilepatternException {
		Git git = new Git(db);
		writeTrashFile("a.b"
		writeTrashFile("a.c"
		writeTrashFile("a/b.b/b"
		writeTrashFile("a/b"
		writeTrashFile("a/c"
		writeTrashFile("a=c"
		writeTrashFile("a=d"
		git.add().addFilepattern("a.b").addFilepattern("a.c")
				.addFilepattern("a/b.b/b").addFilepattern("a/b")
				.addFilepattern("a/c").addFilepattern("a=c")
				.addFilepattern("a=d").call();
