		try (Git git = new Git(db)) {
			FileUtils.mkdirs(new File(db.getWorkTree()
			writeTrashFile("a/c"
			writeTrashFile("b"
			git.add().addFilepattern(".").call();
			RevCommit commit1 = git.commit().setMessage("add folder a & file b")
					.call();
			Ref branch_1 = git.branchCreate().setName("branch_1").call();
			git.rm().addFilepattern("a").call();
			RevCommit commit2 = git.commit().setMessage("delete folder a").call();

			TreeWalk tw = new TreeWalk(db);
			tw.addTree(commit1.getTree());
			tw.addTree(commit2.getTree());
			List<DiffEntry> scan = DiffEntry.scan(tw);
			assertEquals(1
			assertEquals(FileMode.MISSING
			assertEquals(FileMode.TREE

			writeTrashFile("a"

			FileEntry entry = new FileTreeIterator.FileEntry(new File(
					db.getWorkTree()
			assertEquals(FileMode.REGULAR_FILE

			CheckoutConflictException exception = null;
			try {
				git.checkout().setName(branch_1.getName()).call();
			} catch (CheckoutConflictException e) {
				exception = e;
			}
			assertNotNull(exception);
			assertEquals(1
			assertEquals("a"
