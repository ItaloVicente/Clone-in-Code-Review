	@Test
	public void testMergeDirtyIndex() throws Exception {
		Git git = new Git(db);

		File a = writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit firstMasterCommit = git.commit().setMessage("first master")
				.call();

		createBranch(firstMasterCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		RevCommit secondMasterCommit = git.commit().setMessage("second master")
				.call();
