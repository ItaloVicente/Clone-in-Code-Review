		try (Git git = new Git(db)) {
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a & file b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			File fileA = new File(db.getWorkTree()
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			FileUtils.delete(fileA);

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE
		}
