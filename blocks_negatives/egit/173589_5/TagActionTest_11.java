		touchAndSubmit(null);

		someLightTagCommit = repo.exactRef(Constants.HEAD).getObjectId();
		tag = new TagBuilder();
		tag.setTag("SomeLightTag");
		tag.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR));
		tag.setObjectId(someLightTagCommit, Constants.OBJ_COMMIT);
		top = new TagOperation(repo, tag, false, false);
		top.execute(null);
