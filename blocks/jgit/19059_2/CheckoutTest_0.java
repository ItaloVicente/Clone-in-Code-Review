	@Test
	public void testCheckoutExistingBranch() throws Exception {
		Git git = new Git(db);
		writeTrashFile("a"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit file a").call();
		git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree()
		writeTrashFile("a/b"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("commit folder a").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a"
		git.add().addFilepattern(".").call();

		assertEquals(
				"error: Your local changes to the following files would be overwritten by checkout:"
				execute("git checkout branch_1"));
	}

