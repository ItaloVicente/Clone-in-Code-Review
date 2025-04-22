		IResourceVariantTree baseTree = grvts.getBaseTree();

		IResourceVariant actual = commonAssertionsForBaseTree(baseTree,
				mainJava);
		assertEquals(commit.abbreviate(7).name() + "... (J. Git)",
				actual.getContentIdentifier());
	}

	/**
	 * Both source and destination branches has some different commits but they
	 * has also common ancestor. This situation is described more detailed in
	 * bug #317934
	 *
	 * This test passes when it is run as a stand alone test case, but it fails
	 * when it is run as part of test suite. It throws NPE when it try's to
	 * checkout master branch.
	 *
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void shouldReturnCommonAncestorAsBase() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		RevCommit commit = testRepo.appendContentAndCommit(iProject, file,
				"class Main {}", "initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
		ObjectId fileId = findFileId(commit, mainJava);
