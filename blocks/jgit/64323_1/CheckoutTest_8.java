		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("add files a & b").call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add file a").call();

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			git.rm().addFilepattern("a").call();
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			git.add().addFilepattern(".").call();

			entry = new FileTreeIterator.FileEntry(new File(db.getWorkTree()
					db.getFS());
			assertEquals(FileMode.TREE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(1
			assertEquals("a"
