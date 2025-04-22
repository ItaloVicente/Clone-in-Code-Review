		Repository repo = repository1.getRepository();
		assertTrue("Tags should be empty", repo.getRefDatabase()
				.getRefsByPrefix(Constants.R_TAGS).isEmpty());
		RevCommit commit = repo.parseCommit(repo.resolve("refs/heads/master"));
		TagOperation top = new TagOperation(repo)
				.setName("TheNewTag")
				.setAnnotated(false)
				.setTagger(RawParseUtils.parsePersonIdent(TestUtils.AUTHOR))
				.setTarget(repo.parseCommit(repo.resolve("refs/heads/master")));
