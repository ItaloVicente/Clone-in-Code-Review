		try (Git git = new Git(nestedRepo)) {
			WorkingTreeIterator customIterator = new FileTreeIterator(
					nestedRepo
			git.add().setWorkingTreeIterator(customIterator).addFilepattern(".")
					.call();
			assertEquals(
					"[sub/a.txt
							+ "[sub/nested/b.txt
					indexState(nestedRepo
		}
