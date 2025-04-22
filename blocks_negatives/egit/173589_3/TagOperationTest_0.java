		assertTrue("Tags should be empty", repository1.getRepository()
				.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).isEmpty());
		TagBuilder newTag = new TagBuilder();
		newTag.setTag("TheNewTag");
		newTag.setMessage("Well, I'm the tag");
		newTag.setTagger(RawParseUtils.parsePersonIdent(TestUtils.AUTHOR));
		newTag.setObjectId(repository1.getRepository()
				.resolve("refs/heads/master"), Constants.OBJ_COMMIT);
		TagOperation top = new TagOperation(repository1.getRepository(),
				newTag, false);
