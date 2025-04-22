		Git git = new Git(db);
		String path = "file";
		writeTrashFile(path, "content");
		git.add().addFilepattern(path).call();
		String path2 = "file2";
		writeTrashFile(path2, "content");
		String path3 = "file3";
		writeTrashFile(path3, "some content");
		git.add().addFilepattern(path2).addFilepattern(path3).call();
		git.commit().setMessage("commit").call();
		assumeUnchanged(path2);
		assumeUnchanged(path3);
		writeTrashFile(path, "more content");
		deleteTrashFile(path3);

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db, Constants.HEAD, iterator);
		diff.diff();
		assertEquals(2, diff.getAssumeUnchanged().size());
		assertEquals(1, diff.getModified().size());
		assertEquals(0, diff.getChanged().size());
		assertTrue(diff.getAssumeUnchanged().contains("file2"));
		assertTrue(diff.getAssumeUnchanged().contains("file3"));
		assertTrue(diff.getModified().contains("file"));

		git.add().addFilepattern(".").call();

		iterator = new FileTreeIterator(db);
		diff = new IndexDiff(db, Constants.HEAD, iterator);
		diff.diff();
		assertEquals(2, diff.getAssumeUnchanged().size());
		assertEquals(0, diff.getModified().size());
		assertEquals(1, diff.getChanged().size());
		assertTrue(diff.getAssumeUnchanged().contains("file2"));
		assertTrue(diff.getAssumeUnchanged().contains("file3"));
		assertTrue(diff.getChanged().contains("file"));
		assertEquals(Collections.EMPTY_SET, diff.getUntrackedFolders());
