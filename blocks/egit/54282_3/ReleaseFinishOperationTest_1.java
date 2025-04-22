		InitParameters initParameters = new InitParameters();
		initParameters.setDevelop(DEVELOP);
		initParameters.setMaster(MASTER);
		initParameters.setFeature(FEATURE_PREFIX);
		initParameters.setRelease(RELEASE_PREFIX);
		initParameters.setHotfix(HOTFIX_PREFIX);
		initParameters.setVersionTag(MY_VERSION_TAG);
		new InitOperation(repository, initParameters).execute(null);
