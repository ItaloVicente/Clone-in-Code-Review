		Git git = Git.wrap(db);

		writeTrashFile(fname, "a");
		git.add().addFilepattern(fname).call();

		String linkName = "link";
		File link = writeLink(linkName, fname).toFile();
		git.add().addFilepattern(linkName).call();
		git.commit().setMessage("Added file and link").call();

		assertWorkDir(mkmap(linkName, "a", fname, "a"));

		FileUtils.delete(link);
		FileUtils.mkdirs(new File(link, "dummyDir"));
		assertTrue("Link must be a directory now", link.isDirectory());

		assertFalse("Must not delete non empty directory", link.delete());

		writeTrashFile(fname, "b");
		assertWorkDir(mkmap(fname, "b", linkName + "/dummyDir", "/"));

		git.checkout().setStartPoint(Constants.HEAD)
				.addPath(fname).addPath(linkName).call();

		assertWorkDir(mkmap(fname, "a", linkName, "a"));

		Status st = git.status().call();
		assertTrue(st.isClean());
