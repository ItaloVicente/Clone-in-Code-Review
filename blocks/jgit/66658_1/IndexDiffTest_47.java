		try (Git git = new Git(db)) {
			IndexDiff diff = new IndexDiff(db
					new FileTreeIterator(db));
			diff.diff();
			assertEquals(Collections.EMPTY_SET

			writeTrashFile("readme"
			writeTrashFile("src/com/A.java"
			writeTrashFile("src/com/B.java"
			writeTrashFile("src/org/A.java"
			writeTrashFile("src/org/B.java"
			writeTrashFile("target/com/A.java"
			writeTrashFile("target/com/B.java"
			writeTrashFile("target/org/A.java"
			writeTrashFile("target/org/B.java"

			git.add().addFilepattern("src").addFilepattern("readme").call();
			git.commit().setMessage("initial").call();

			diff = new IndexDiff(db
					new FileTreeIterator(db));
			diff.diff();
			assertEquals(new HashSet<String>(Arrays.asList("target"))
					diff.getUntrackedFolders());

			writeTrashFile("src/tst/A.java"
			writeTrashFile("src/tst/B.java"

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(new HashSet<String>(Arrays.asList("target"
					diff.getUntrackedFolders());

			git.rm().addFilepattern("src/com/B.java").addFilepattern("src/org")
					.call();
			git.commit().setMessage("second").call();
			writeTrashFile("src/org/C.java"

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(
					new HashSet<String>(Arrays.asList("src/org"
							"target"))
					diff.getUntrackedFolders());
		}
