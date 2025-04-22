	@Test
	public void testOverwriteUntrackedIgnoredFile() throws IOException
			GitAPIException {
		String fname="file.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("create file").call();

		git.branchCreate().setName("side").call();

		writeTrashFile(fname
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("modify file").call();

		git.checkout().setName("side").call();
		git.rm().addFilepattern(fname).call();
		writeTrashFile(".gitignore"
		git.add().addFilepattern(".gitignore").call();
		git.commit().setMessage("delete and ignore file").call();

		writeTrashFile(fname
		git.checkout().setName("master").call();
		assertWorkDir(mkmap(fname
		assertTrue(git.status().call().isClean());
	}

