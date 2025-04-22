
	@Test
	public void testCreateBranchOnTagCheckout() throws Exception {

		git.tag().setName("tag").call();

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Third commit").call();

		try {
			git.checkout().setName("tag").setCreateBranch(true)
					.setStartPoint("refs/tags/tag").call();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertNotNull(db.getRef("tag"));
		assertEquals("tag"
		assertEquals("[Test.txt
				indexState(db
	}
