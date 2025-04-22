		Git git = Git.wrap(db);

		File file = writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();
		git.commit().setMessage("Added file").call();

		assertWorkDir(mkmap(fname, "a"));

		FileUtils.delete(file);

		writeTrashFile(fname + "/dir", "file1", "c");
		git.add().addFilepattern(fname + "/dir/file1").call();

		writeTrashFile(fname + "/dir", "file2", "d");

		assertTrue("File must be a directory now", file.isDirectory());
		assertFalse("Must not delete non empty directory", file.delete());

		assertWorkDir(
				mkmap(fname + "/dir/file1", "c", fname + "/dir/file2", "d"));

		git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();
		assertWorkDir(mkmap(fname, "a"));

		Status st = git.status().call();
		assertTrue(st.isClean());
