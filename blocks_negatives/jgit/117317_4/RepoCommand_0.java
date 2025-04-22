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
