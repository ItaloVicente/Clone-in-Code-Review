		GitResourceVariantTreeSubscriber grvts = createGitResourceVariantTreeSubscriber(
				Constants.HEAD, "test");
		grvts.getBaseTree();
		IResourceVariantTree remoteTree = grvts.getRemoteTree();

		assertNotNull(remoteTree);
		assertTrue(remoteTree instanceof GitRemoteResourceVariantTree);
		IResourceVariant resourceVariant = remoteTree
				.getResourceVariant(mainJava);
		assertNotNull(resourceVariant);
		assertTrue(resourceVariant instanceof GitRemoteResource);
		assertEquals(fileId.getName(), resourceVariant.getContentIdentifier());
