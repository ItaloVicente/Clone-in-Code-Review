		checkCallable();
		if (path == null || path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().pathNotConfigured);
		if (uri == null || uri.length() == 0)
			throw new IllegalArgumentException(JGitText.get().uriNotConfigured);

		if (repo.isBare()) {
			bareProjects = new ArrayList<Project>();
			if (author == null)
				author = new PersonIdent(repo);
			if (callback == null)
				callback = new DefaultRemoteReader();
		} else
			git = new Git(repo);

		XmlManifest manifest = new XmlManifest(this, path, uri, groups);
