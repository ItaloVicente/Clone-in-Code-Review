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
