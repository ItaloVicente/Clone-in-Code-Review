
	@Test
	public void testExistingBranch() throws Exception {
		assertEquals("A branch named 'master' already exists."
				execute("git branch master")[0]);
	}
