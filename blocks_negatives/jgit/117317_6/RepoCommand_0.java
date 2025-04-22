		try {
			checkCallable();
			if (baseUri == null) {
			}
			if (inputStream == null) {
				if (manifestPath == null || manifestPath.length() == 0)
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				try {
					inputStream = new FileInputStream(manifestPath);
				} catch (IOException e) {
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				}
			}

			if (repo.isBare()) {
				bareProjects = new ArrayList<>();
				if (author == null)
					author = new PersonIdent(repo);
				if (callback == null)
					callback = new DefaultRemoteReader();
			} else
				git = new Git(repo);

			ManifestParser parser = new ManifestParser(
					includedReader, manifestPath, branch, baseUri, groupsParam, repo);
