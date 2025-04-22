	@Test
	public void testList_CLI() throws Exception {
		assertEquals("* master 9c58deb Initial commit"
				execute("git branch -v")[0]);
	}

