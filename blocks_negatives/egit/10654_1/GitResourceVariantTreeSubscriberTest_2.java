		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		testRepo.appendContentAndCommit(iProject, file, "// test 1",
				"second commit");

		testRepo.checkoutBranch(Constants.R_HEADS + Constants.MASTER);
		testRepo.appendContentAndCommit(iProject, file, "// test 2",
				"third commit");

		GitResourceVariantTreeSubscriber grvts = createGitResourceVariantTreeSubscriber(
				Constants.HEAD, Constants.R_HEADS + "test");
		grvts.getBaseTree();
		IResourceVariantTree baseTree = grvts.getBaseTree();

		IResourceVariant actual = commonAssertionsForBaseTree(baseTree,
				mainJava);
		assertEquals(fileId.getName(), actual.getContentIdentifier());
