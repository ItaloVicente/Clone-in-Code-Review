		Repository repo = repository1.getRepository();
		assertTrue("Tags should be empty", repo.getRefDatabase()
				.getRefsByPrefix(Constants.R_TAGS).isEmpty());
		PersonIdent author = RawParseUtils.parsePersonIdent(TestUtils.AUTHOR);
		TagOperation top = new TagOperation(repo)
				.setAnnotated(true)
				.setForce(false).setName("TheNewTag")
				.setMessage("Well, I'm the tag")
				.setTagger(author)
				.setTarget(repo.parseCommit(repo.resolve("refs/heads/master")));
