		Git git = Git.wrap(db);

		writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName, fname).toFile();
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName, "a", fname, "a"));

		FileUtils.delete(link);
		FileUtils.mkdir(link);
		assertTrue("Link must be a directory now", link.isDirectory());

		writeTrashFile(fname, "b");
		assertWorkDir(mkmap(fname, "b", linkName, "/"));

		git.checkout().setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(linkName).call();

		assertWorkDir(mkmap(fname, "a", linkName, "a"));

		Status st = git.status().call();
		assertTrue(st.isClean());
