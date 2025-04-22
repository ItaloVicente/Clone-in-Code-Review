		try (Git git = new Git(db)) {
			writeTrashFile("b"
			writeTrashFile("a"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add file b & file a").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			File folderA = new File(db.getWorkTree()
			FileUtils.mkdirs(folderA);
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add folder a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.TREE

			FileUtils.delete(folderA

			git.checkout().setName(branch_1.getName()).call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.REGULAR_FILE
		}
