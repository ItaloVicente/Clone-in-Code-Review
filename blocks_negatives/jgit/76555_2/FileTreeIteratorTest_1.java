		Git git = new Git(nestedRepo);

		FileTreeIterator customIterator =
				new FileTreeIterator(nestedRepo, NO_GITLINKS_STRATEGY);
		File r = new File(nestedRepo.getWorkTree(), "sub");

		FileTreeIterator childIterator =
				new FileTreeIterator(customIterator, r, nestedRepo.getFS());
		git.add().setWorkingTreeIterator(childIterator).addFilepattern(".").call();
		assertEquals(
				"[sub/a.txt, mode:100644, content:content]" +
				"[sub/nested/b.txt, mode:100644, content:content b]",
				indexState(nestedRepo, CONTENT));
