		Git git = Git.wrap(db);

		File file = writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		FileUtils.delete(file);
		FileUtils.mkdirs(new File(file, "dummyDir"));
		assertTrue("File must be a directory now", file.isDirectory());
		assertFalse("Must not delete non empty directory", file.delete());

		assertWorkDir(mkmap(fname + "/dummyDir", "/"));

		git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();

		assertWorkDir(mkmap(fname, "a"));

		Status st = git.status().call();
		assertTrue(st.isClean());
