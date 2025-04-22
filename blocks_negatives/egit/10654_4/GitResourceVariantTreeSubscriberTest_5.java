	/**
	 * This test passes when it is run as a stand alone test case, but it fails
	 * when it is run as part of test suite. It throws NPE when it try's to
	 * checkout master branch.
	 *
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void shouldReturnRemoteTree() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file,
				"class Main {}", "initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);

		testRepo.createAndCheckoutBranch(Constants.HEAD, Constants.R_HEADS
				+ "test");
		RevCommit commit = testRepo.appendContentAndCommit(iProject, file, "// test 1",
				"second commit");
		ObjectId fileId = findFileId(commit, mainJava);

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
