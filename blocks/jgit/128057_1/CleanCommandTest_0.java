	@Test
	public void testCleanDirsWithPrefixFolder() throws Exception {
		String path = "sub/foo.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		Status beforeCleanStatus = git.status().call();
		assertTrue(beforeCleanStatus.getAdded().contains(path));

		Set<String> cleanedFiles = git.clean().setCleanDirectories(true).call();

		assertTrue(!cleanedFiles.contains(path + "/"));

		assertTrue(cleanedFiles.contains("File2.txt"));
		assertTrue(cleanedFiles.contains("File3.txt"));
		assertTrue(!cleanedFiles.contains("sub-noclean/File1.txt"));
		assertTrue(cleanedFiles.contains("sub-noclean/File2.txt"));
		assertTrue(cleanedFiles.contains("sub-clean/"));
		assertTrue(cleanedFiles.size() == 4);
	}

