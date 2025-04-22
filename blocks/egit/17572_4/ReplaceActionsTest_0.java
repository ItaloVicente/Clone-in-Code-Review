
		git.checkout().setCreateBranch(true).setName("side").call();
		touch(PROJ1, "folder/test.txt", "side");
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("Side commit").call();

		git.checkout().setName("master").call();
		touch(PROJ1, "folder/test2.txt", "master");
		git.commit().setAll(true).setMessage("Master commit").call();

		git.merge().include(sideCommit).call();

		String contentAfterMerge = getTestFileContent();
		assertEquals("side", contentAfterMerge);

