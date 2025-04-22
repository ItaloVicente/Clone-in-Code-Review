		Git git = new Git(db);
		writeTrashFile("a", "Hello world a");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("commit file a").call();
		git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree(), "a"));
		writeTrashFile("a/b", "Hello world b");
		git.add().addFilepattern("a/b").call();
		git.commit().setMessage("commit folder a").call();
		git.rm().addFilepattern("a").call();
		writeTrashFile("a", "New Hello world a");
		git.add().addFilepattern(".").call();

		String[] execute = execute("git checkout branch_1");
		assertEquals(
				"error: Your local changes to the following files would be overwritten by checkout:",
				execute[0]);
		assertEquals("\ta", execute[1]);
