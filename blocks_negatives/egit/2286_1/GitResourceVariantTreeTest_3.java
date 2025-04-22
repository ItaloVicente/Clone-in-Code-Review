	/**
	 * Check if getResourceVariant() does return the same resource that was
	 * committed. Passes only when it is run as a single test, not as a part of
	 * largest test suite
	 *
	 * @throws Exception
	 */
	@Test
	public void shoulReturnSameResourceVariant() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, "class Main {}",
				"initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		IResourceVariant actual = grvt.getResourceVariant(mainJava);
		assertNotNull(actual);
		assertEquals(fileName, actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = getBytesAndCloseStream(actualIn);
		InputStream expectedIn = mainJava.getContents();
		byte[] expectedByte = getBytesAndCloseStream(expectedIn);
		assertArrayEquals(expectedByte, actualByte);
	}

	/**
	 * Create and commit Main.java file in master branch, then create branch
	 * "test" checkout nearly created branch and modify Main.java file.
	 * getResourceVariant() should obtain Main.java file content from "master"
	 * branch. Passes only when it is run as a single test, not as a part of
	 * largest test suite
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldReturnDifferentResourceVariant() throws Exception {
		String fileName = "Main.java";
		File file = testRepo.createFile(iProject, fileName);
		testRepo.appendContentAndCommit(iProject, file, "class Main {}",
				"initial commit");
		IFile mainJava = testRepo.getIFile(iProject, file);

		testRepo.createAndCheckoutBranch(Constants.R_HEADS + Constants.MASTER,
				Constants.R_HEADS + "test");
		testRepo.appendContentAndCommit(iProject, file, "// test",
				"first commit");
		GitSynchronizeData data = new GitSynchronizeData(repo, HEAD, MASTER,
				false);
		GitSynchronizeDataSet dataSet = new GitSynchronizeDataSet(data);

		GitResourceVariantTree grvt = new GitRemoteResourceVariantTree(dataSet);

		IResourceVariant actual = grvt.getResourceVariant(mainJava);
		assertNotNull(actual);
		assertEquals(fileName, actual.getName());

		InputStream actualIn = actual.getStorage(new NullProgressMonitor())
				.getContents();
		byte[] actualByte = getBytesAndCloseStream(actualIn);
		InputStream expectedIn = mainJava.getContents();
		byte[] expectedByte = getBytesAndCloseStream(expectedIn);

		if (Arrays.equals(expectedByte, actualByte))
			fail();
		else
			assertTrue(true);
	}

	private byte[] getBytesAndCloseStream(InputStream stream) throws Exception {
		try {
			byte[] actualByte = new byte[stream.available()];
			stream.read(actualByte);
			return actualByte;
		} finally {
			stream.close();
		}
	}
