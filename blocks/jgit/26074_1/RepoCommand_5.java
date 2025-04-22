		if (repo.isBare()) {
			bareProjects = new ArrayList<Project>();
			if (author == null)
				author = new PersonIdent(repo);
			if (callback == null)
				callback = new DefaultGetHeadFromUri();
		} else
			git = new Git(repo);

