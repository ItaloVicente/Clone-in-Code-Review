		if (gitdir == null)
			gitdir = new File(bare ? "." : Constants.DOT_GIT);
		else
			bare = true;
		db = new FileRepository(gitdir);
		db.create(bare);
