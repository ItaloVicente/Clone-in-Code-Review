	@Test
	public void testDelete() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		RevTag tag = git.tag().setName("tag").call();
		assertEquals(1

		List<String> deleted = git.tagDelete().setTags(tag.getTagName())
				.call();
		assertEquals(1
		assertEquals(tag.getTagName()
				Repository.shortenRefName(deleted.get(0)));
		assertEquals(0

		RevTag tag1 = git.tag().setName("tag1").call();
		RevTag tag2 = git.tag().setName("tag2").call();
		assertEquals(2
		deleted = git.tagDelete()
				.setTags(tag1.getTagName()
		assertEquals(2
		assertEquals(0
	}

	@Test
	public void testDeleteFullName() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		RevTag tag = git.tag().setName("tag").call();
		assertEquals(1

		List<String> deleted = git.tagDelete()
				.setTags(Constants.R_TAGS + tag.getTagName()).call();
		assertEquals(1
		assertEquals(Constants.R_TAGS + tag.getTagName()
		assertEquals(0
	}

	@Test
	public void testDeleteEmptyTagNames() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		List<String> deleted = git.tagDelete().setTags().call();
		assertEquals(0
	}

	@Test
	public void testDeleteNonExisting() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		List<String> deleted = git.tagDelete().setTags("tag").call();
		assertEquals(0
	}

	@Test
	public void testDeleteBadName() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		List<String> deleted = git.tagDelete().setTags("bad~tag~name")
				.call();
		assertEquals(0
	}

