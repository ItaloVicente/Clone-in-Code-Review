		Git git = new Git(db);
		File fileA = writeTrashFile("a", "Hello world a");
		writeTrashFile("b", "Hello world b");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add files a & b").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		writeTrashFile("a", "b");
		git.add().addFilepattern("a").call();
		git.commit().setMessage("modify file a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree(), "a"), db.getFS());
		assertEquals(FileMode.REGULAR_FILE, entry.getMode());

		FileUtils.delete(fileA);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree(), "a"),
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE, entry.getMode());
		assertEquals("Hello world a", read(fileA));
