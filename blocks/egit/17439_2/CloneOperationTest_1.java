		String fullRefName = "refs/heads/master";
		cloneAndAssert(fullRefName);

		assertTrue(new File(workdir2, "file1.txt").exists());
		assertTrue(new File(workdir2, "file2.txt").exists());
		assertTrue(new File(workdir2, "file3.txt").exists());
	}

	@Test
	public void testCloneBranch() throws Exception {
		String branchName = "dev";
		cloneAndAssert(branchName);

		assertTrue(new File(workdir2, "file1.txt").exists());
		assertTrue(new File(workdir2, "file2.txt").exists());
		assertFalse(new File(workdir2, "file3.txt").exists());
	}

	@Test
	public void testCloneTag() throws Exception {
		String tagName = "tag";
		cloneAndAssert(tagName);

		assertTrue(new File(workdir2, "file1.txt").exists());
		assertFalse(new File(workdir2, "file2.txt").exists());
		assertFalse(new File(workdir2, "file3.txt").exists());
	}

	private void cloneAndAssert(String refName) throws Exception {
