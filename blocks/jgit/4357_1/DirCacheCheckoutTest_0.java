	@Test
	public void testResetHardFileReplacedByDirectory() throws IOException
			NoHeadException
			JGitInternalException
			NoFilepatternException {
		Git git = new Git(db);
		writeTrashFile("f/g"
		git.add().addFilepattern(".").call();
		RevCommit id1 = git.commit().setMessage("c1").call();
		deleteTrashFile("f/g");
		deleteTrashFile("f");
		git.rm().addFilepattern(".").call();
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		git.commit().setMessage("c2").call();
		git.reset().setMode(ResetType.HARD).setRef(id1.getName()).call();
	}

