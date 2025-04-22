	public void testUnchangedSimple() throws IOException
			NoFilepatternException {
		writeTrashFile("a.b"
		writeTrashFile("a.c"
		writeTrashFile("a=c"
		writeTrashFile("a=d"
		Git git = new Git(db);
		git.add().addFilepattern("a.b").call();
		git.add().addFilepattern("a.c").call();
		git.add().addFilepattern("a=c").call();
		git.add().addFilepattern("a=d").call();
