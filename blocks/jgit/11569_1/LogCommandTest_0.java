		tagCmd = git.tag();
		tagCmd.setName("tagtree");
		tagCmd.setObjectId(commits.get(0).getTree());
		tagCmd.setTagger(new PersonIdent(db));
		tagCmd.call();

