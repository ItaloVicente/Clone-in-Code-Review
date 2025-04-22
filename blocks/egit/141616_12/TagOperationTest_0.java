	@Test
	public void addEmptyAnnotatedTag() throws Exception {
		assertTrue("Tags should be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		TagBuilder newTag = new TagBuilder();
		newTag.setTag("TheNewTag");
		newTag.setMessage("");
		newTag.setTagger(RawParseUtils.parsePersonIdent(TestUtils.AUTHOR));
		ObjectId headCommit = repository1.getRepository()
				.resolve("refs/heads/master");
		newTag.setObjectId(headCommit, Constants.OBJ_COMMIT);
		TagOperation top = new TagOperation(repository1.getRepository(), newTag,
				false, true);
		top.execute(new NullProgressMonitor());
		assertFalse("Tags should not be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		assertIsAnnotated("TheNewTag", headCommit, "");
	}

	@Test
	public void addNullAnnotatedTag() throws Exception {
		assertTrue("Tags should be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		TagBuilder newTag = new TagBuilder();
		newTag.setTag("TheNewTag");
		newTag.setTagger(RawParseUtils.parsePersonIdent(TestUtils.AUTHOR));
		ObjectId headCommit = repository1.getRepository()
				.resolve("refs/heads/master");
		newTag.setObjectId(headCommit, Constants.OBJ_COMMIT);
		TagOperation top = new TagOperation(repository1.getRepository(), newTag,
				false, true);
		top.execute(new NullProgressMonitor());
		assertFalse("Tags should not be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		assertIsAnnotated("TheNewTag", headCommit, null);
	}

	@Test
	public void addLightweightTag() throws Exception {
		assertTrue("Tags should be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		TagBuilder newTag = new TagBuilder();
		newTag.setTag("TheNewTag");
		newTag.setTagger(RawParseUtils.parsePersonIdent(TestUtils.AUTHOR));
		ObjectId headCommit = repository1.getRepository()
				.resolve("refs/heads/master");
		newTag.setObjectId(headCommit, Constants.OBJ_COMMIT);
		TagOperation top = new TagOperation(repository1.getRepository(), newTag,
				false, false);
		top.execute(new NullProgressMonitor());
		assertFalse("Tags should not be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		assertIsLightweight("TheNewTag", headCommit);
	}

	private void assertIsAnnotated(String tag, ObjectId target, String message)
			throws Exception {
		Ref ref = repository1.getRepository().exactRef(Constants.R_TAGS + tag);
		ObjectId obj = ref.getObjectId();
		try (RevWalk walk = new RevWalk(repository1.getRepository())) {
			RevTag t = walk.parseTag(obj);
			if (message != null) {
				assertEquals("Unexpected tag message", message,
						t.getFullMessage());
			}
			assertEquals("Unexpected commit for tag " + t.getName(), target,
					walk.peel(t));
		}
	}

	private void assertIsLightweight(String tag, ObjectId target)
			throws Exception {
		Ref ref = repository1.getRepository().exactRef(Constants.R_TAGS + tag);
		ObjectId obj = ref.getObjectId();
		assertEquals("Unexpected commit for tag " + ref.getName(), target, obj);
	}

