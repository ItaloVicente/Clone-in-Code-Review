	@Test
	public void testShouldNotBlowUpIfThereAreNoTagsInRepository()
			throws Exception {
		Git git = new Git(db);
		git.add().addFilepattern("*").call();
		git.commit().setMessage("initial commit").call();
		List<RevTag> list = git.tagList().call();
		assertEquals(0
	}

	@Test
	public void testShouldNotBlowUpIfThereAreNoCommitsInRepository()
			throws Exception {
		Git git = new Git(db);
		List<RevTag> list = git.tagList().call();
		assertEquals(0
	}

	@Test
	public void testListAllTagsInRepositoryInOrder() throws Exception {
		Git git = new Git(db);
		git.add().addFilepattern("*").call();
		git.commit().setMessage("initial commit").call();

		git.tag().setName("v3").call();
		git.tag().setName("v2").call();
		git.tag().setName("v10").call();

		List<RevTag> list = git.tagList().call();

		assertEquals(3
		assertEquals("v10"
		assertEquals("v2"
		assertEquals("v3"
	}

