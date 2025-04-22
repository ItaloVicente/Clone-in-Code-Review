		Git git = new Git(db);
		writeTrashFile("b", "Hello world b");
		writeTrashFile("a", "b");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		git.commit().setMessage("delete file a").call();

		FileUtils.mkdirs(new File(db.getWorkTree(), "a"));
		writeTrashFile("a/c", "Hello world c");

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree(), "a"), db.getFS());
		assertEquals(FileMode.TREE, entry.getMode());

		CheckoutConflictException exception = null;
		try {
			git.checkout().setName(branch_1.getName()).call();
		} catch (CheckoutConflictException e) {
			exception = e;
