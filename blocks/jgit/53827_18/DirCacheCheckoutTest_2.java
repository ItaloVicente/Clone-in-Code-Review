	public void testCheckoutPathesDeletesFile()
			throws IOException
		String fname1 = "file1.txt";
		String fname2 = "file2.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname1
		git.add().addFilepattern(fname1).call();
		git.commit().setMessage("create file1").call();

		git.branchCreate().setName("side").call();

		writeTrashFile(fname2
		git.add().addFilepattern(fname2).call();
		git.commit().setMessage("create file2").call();
		assertWorkDir(mkmap(fname1

		git.checkout().setName("side").addPath(fname1).call();
		assertWorkDir(mkmap(fname1

		try {
			git.checkout().setName("side").addPath(fname2).call();
			fail("did not throw exception");
		} catch (GitAPIException e) {
			assertWorkDir(mkmap(fname1
		}
	}

	public void testCheckoutPathesReplacesFileByFolder()
			throws IOException
		String fname1 = "file1.txt";
		Git git = Git.wrap(db);

		writeTrashFile(fname1 + "/a.txt"
		git.add().addFilepattern(fname1).call();
		git.commit().setMessage("create folder").call();
		assertWorkDir(mkmap(fname1 + "/a.txt"

		git.branchCreate().setName("side").call();

		git.rm().addFilepattern(fname1).call();
		writeTrashFile(fname1
		git.add().addFilepattern(fname1).call();
		git.commit().setMessage("replace folder with file").call();
		assertWorkDir(mkmap(fname1

		git.checkout().setName("side").addPath(fname1)
				.addPath(fname1 + "/a.txt").call();
		assertWorkDir(mkmap(fname1 + "/a.txt"
	}

