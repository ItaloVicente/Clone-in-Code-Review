		Git git = new Git(nestedRepo);
		WorkingTreeIterator customIterator =
				new FileTreeIterator(nestedRepo, NO_GITLINKS_STRATEGY);
		git.add().setWorkingTreeIterator(customIterator)
				.addFilepattern(".").call();
		assertEquals(
				"[sub/a.txt, mode:100644, content:content]" +
				"[sub/nested/b.txt, mode:100644, content:content b]",
				indexState(nestedRepo, CONTENT));

