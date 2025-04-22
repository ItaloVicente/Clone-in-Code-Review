		return uris;
	}

	@Override
	ChangeList createChangeList(Repository repo, String uri) {
		return new ChangeList(repo, uri);
