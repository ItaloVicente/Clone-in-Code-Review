		try (Git git = new Git(nestedRepo)) {
			FileTreeIterator customIterator = new FileTreeIterator(nestedRepo
					NO_GITLINKS_STRATEGY);
			File r = new File(nestedRepo.getWorkTree()

			FileTreeIterator childIterator = new FileTreeIterator(
					customIterator
			git.add().setWorkingTreeIterator(childIterator).addFilepattern(".")
					.call();
			assertEquals(
					"[sub/a.txt
							+ "[sub/nested/b.txt
					indexState(nestedRepo
		}
