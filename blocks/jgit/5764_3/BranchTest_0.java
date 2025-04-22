
	@Test
	public void testExistingBranch() throws Exception {
		assertEquals("fatal: A branch named 'master' already exists."
				execute("git branch master")[0]);
	}
