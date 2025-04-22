		Git git = new Git(db);
		writeTrashFile("b", "Hello world b");
		writeTrashFile("a", "b");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add file b & file a").call();
		Ref branch_1 = git.branchCreate().setName("branch_1").call();
		git.rm().addFilepattern("a").call();
		File folderA = new File(db.getWorkTree(), "a");
		FileUtils.mkdirs(folderA);
		writeTrashFile("a/c", "Hello world c");
		git.add().addFilepattern(".").call();
		git.commit().setMessage("add folder a").call();

		FileEntry entry = new FileTreeIterator.FileEntry(new File(
				db.getWorkTree(), "a"), db.getFS());
		assertEquals(FileMode.TREE, entry.getMode());

		FileUtils.delete(folderA, FileUtils.RECURSIVE);

		git.checkout().setName(branch_1.getName()).call();

		entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree(), "a"),
				db.getFS());
		assertEquals(FileMode.REGULAR_FILE, entry.getMode());
