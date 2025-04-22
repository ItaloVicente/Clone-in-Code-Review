
		tag = new TagBuilder();
		tag.setTag("SomeLightTag");
		tag.setTagger(RawParseUtils.parsePersonIdent(TestUtil.TESTAUTHOR));
		tag.setObjectId(repo.resolve(repo.getFullBranch()),
				Constants.OBJ_COMMIT);
		top = new TagOperation(repo, tag, false, false);
		top.execute(null);

