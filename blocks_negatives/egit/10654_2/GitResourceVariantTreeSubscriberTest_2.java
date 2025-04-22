		GitResourceVariantTreeSubscriber grvts = createGitResourceVariantTreeSubscriber(
				Constants.HEAD, Constants.R_HEADS + "test");
		grvts.getBaseTree();
		IResourceVariantTree baseTree = grvts.getBaseTree();

		IResourceVariant actual = commonAssertionsForBaseTree(baseTree,
				mainJava);
		assertEquals(fileId.getName(), actual.getContentIdentifier());
