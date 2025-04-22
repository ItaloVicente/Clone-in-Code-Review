		Git git = new Git(db);
		writeTrashFile("a", "Hello world a");
		writeTrashFile("b", "Hello world b");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		writeTrashFile("a", "b");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("add file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree(), "a"), db.getFS());
		assertEquals(FileMode.REGULAR_FILE, entry.getMode());

		git.rm().addFilepattern("a").call();
		FileUtils.mkdirs(new File(db.getWorkTree(), "a"));
		writeTrashFile("a/c", "Hello world c");
		git.add().addFilepattern(".").call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree(), "a"),
				db.getFS());
		assertEquals(FileMode.TREE, entry.getMode());

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
