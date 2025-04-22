
		someLightTagCommit = repo.exactRef(Constants.HEAD).getObjectId();
		tag = new TagBuilder();
		tag.setTag("SomeLightTag");
		tag.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR));
		tag.setObjectId(someLightTagCommit, Constants.OBJ_COMMIT);
		top = new TagOperation(repo, tag, false, false);
		top.execute(null);

		touchAndSubmit(null);
		headCommit = repo.exactRef(Constants.HEAD).getObjectId();
	}

	private void assertIsAnnotated(String tag, ObjectId target, String message)
			throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		Ref ref = repo.exactRef(Constants.R_TAGS + tag);
		ObjectId obj = ref.getObjectId();
		try (RevWalk walk = new RevWalk(repo)) {
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
		Repository repo = lookupRepository(repositoryFile);
		Ref ref = repo.exactRef(Constants.R_TAGS + tag);
		ObjectId obj = ref.getObjectId();
		assertEquals("Unexpected commit for tag " + ref.getName(), target, obj);
