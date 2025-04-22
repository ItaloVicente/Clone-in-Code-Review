		try (Git git = new Git(db)) {
			String path = "file";
			writeTrashFile(path
			git.add().addFilepattern(path).call();
			String path2 = "file2";
			writeTrashFile(path2
			String path3 = "file3";
			writeTrashFile(path3
			git.add().addFilepattern(path2).addFilepattern(path3).call();
			git.commit().setMessage("commit").call();
			assumeUnchanged(path2);
			assumeUnchanged(path3);
			writeTrashFile(path
			deleteTrashFile(path3);

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();
			assertEquals(2
			assertEquals(1
			assertEquals(0
			assertTrue(diff.getAssumeUnchanged().contains("file2"));
			assertTrue(diff.getAssumeUnchanged().contains("file3"));
			assertTrue(diff.getModified().contains("file"));

			git.add().addFilepattern(".").call();

			iterator = new FileTreeIterator(db);
			diff = new IndexDiff(db
			diff.diff();
			assertEquals(2
			assertEquals(0
			assertEquals(1
			assertTrue(diff.getAssumeUnchanged().contains("file2"));
			assertTrue(diff.getAssumeUnchanged().contains("file3"));
			assertTrue(diff.getChanged().contains("file"));
			assertEquals(Collections.EMPTY_SET
		}
