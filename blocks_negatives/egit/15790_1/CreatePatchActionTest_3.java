
		TagBuilder tag = new TagBuilder();
		tag.setTag(TAG_NAME);
		tag.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR));
		tag.setMessage("I'm a savepoint");
		tag.setObjectId(repo.resolve(repo.getFullBranch()),
				Constants.OBJ_COMMIT);
		TagOperation top = new TagOperation(repo, tag, false);
		top.execute(null);

		waitInUI();
