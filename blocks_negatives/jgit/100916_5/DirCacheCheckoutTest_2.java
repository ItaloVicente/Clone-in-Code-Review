		Git git = Git.wrap(db);

		File file = writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		FileUtils.delete(file);
		FileUtils.mkdir(file);
		assertTrue("File must be a directory now", file.isDirectory());

		assertWorkDir(mkmap(fname, "/"));

		git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();

		assertWorkDir(mkmap(fname, "a"));

		Status st = git.status().call();
		assertTrue(st.isClean());
