		try (Git git = new Git(repo)) {
			git.tag()
				.setName("SomeTag")
				.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR))
				.setMessage("I'm just a little tag")
				.setObjectId(repo.parseCommit(someTagCommit))
				.setSigned(false)
				.call();
