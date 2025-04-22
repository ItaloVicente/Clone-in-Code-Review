	@Test
	public void testAdded() throws IOException {
		writeTrashFile("file1", "file1");
		writeTrashFile("dir/subfile", "dir/subfile");
		Tree tree = new Tree(db);
		tree.setId(insertTree(tree));

		DirCache index = db.lockDirCache();
		DirCacheEditor editor = index.editor();
		editor.add(add(db, trash, "file1"));
		editor.add(add(db, trash, "dir/subfile"));
		editor.commit();
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, tree.getId(), iterator);
		diff.diff();
		assertEquals(2, diff.getAdded().size());
		assertTrue(diff.getAdded().contains("file1"));
		assertTrue(diff.getAdded().contains("dir/subfile"));
		assertEquals(0, diff.getChanged().size());
		assertEquals(0, diff.getModified().size());
		assertEquals(0, diff.getRemoved().size());
		assertEquals(Collections.EMPTY_SET, diff.getUntrackedFolders());
	}

	@Test
	public void testRemoved() throws IOException {
		writeTrashFile("file2", "file2");
		writeTrashFile("dir/file3", "dir/file3");

		Tree tree = new Tree(db);
		tree.addFile("file2");
		tree.addFile("dir/file3");
		assertEquals(2, tree.memberCount());
		tree.findBlobMember("file2").setId(ObjectId.fromString("30d67d4672d5c05833b7192cc77a79eaafb5c7ad"));
		Tree tree2 = (Tree) tree.findTreeMember("dir");
		tree2.findBlobMember("file3").setId(ObjectId.fromString("873fb8d667d05436d728c52b1d7a09528e6eb59b"));
		tree2.setId(insertTree(tree2));
		tree.setId(insertTree(tree));

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, tree.getId(), iterator);
		diff.diff();
		assertEquals(2, diff.getRemoved().size());
		assertTrue(diff.getRemoved().contains("file2"));
		assertTrue(diff.getRemoved().contains("dir/file3"));
		assertEquals(0, diff.getChanged().size());
		assertEquals(0, diff.getModified().size());
		assertEquals(0, diff.getAdded().size());
		assertEquals(Collections.EMPTY_SET, diff.getUntrackedFolders());
	}

	@Test
	public void testModified() throws IOException, GitAPIException {

		writeTrashFile("file2", "file2");
		writeTrashFile("dir/file3", "dir/file3");

		Git git = new Git(db);
		git.add().addFilepattern("file2").addFilepattern("dir/file3").call();

		writeTrashFile("dir/file3", "changed");

		Tree tree = new Tree(db);
		tree.addFile("file2").setId(ObjectId.fromString("0123456789012345678901234567890123456789"));
		tree.addFile("dir/file3").setId(ObjectId.fromString("0123456789012345678901234567890123456789"));
		assertEquals(2, tree.memberCount());

		Tree tree2 = (Tree) tree.findTreeMember("dir");
		tree2.setId(insertTree(tree2));
		tree.setId(insertTree(tree));
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, tree.getId(), iterator);
		diff.diff();
		assertEquals(2, diff.getChanged().size());
		assertTrue(diff.getChanged().contains("file2"));
		assertTrue(diff.getChanged().contains("dir/file3"));
		assertEquals(1, diff.getModified().size());
		assertTrue(diff.getModified().contains("dir/file3"));
		assertEquals(0, diff.getAdded().size());
		assertEquals(0, diff.getRemoved().size());
		assertEquals(0, diff.getMissing().size());
		assertEquals(Collections.EMPTY_SET, diff.getUntrackedFolders());
	}
