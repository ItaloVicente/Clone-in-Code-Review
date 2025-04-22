	@Test
	public void testGitLinkMerging_AddNew() throws Exception {
		assertGitLinkValue(
				testGitLink(null
				LINK_ID3);
	}

	@Test
	public void testGitLinkMerging_Delete() throws Exception {
		assertGitLinkDoesntExist(
				testGitLink(LINK_ID1
						true));
	}

	@Test
	public void testGitLinkMerging_UpdateDelete() throws Exception {
		testGitLink(LINK_ID1
	}

	@Test
	public void testGitLinkMerging_DeleteUpdate() throws Exception {
		testGitLink(LINK_ID1
	}

	@Test
