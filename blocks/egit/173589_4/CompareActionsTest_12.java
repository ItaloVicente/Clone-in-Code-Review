		TagOperation top = new TagOperation(repo)
				.setAnnotated(true)
				.setName("SomeTag")
				.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR))
				.setMessage("I'm just a little tag")
				.setTarget(repo.parseCommit(repo.resolve(repo.getFullBranch())));
		commitOfTag = top.getTarget();
