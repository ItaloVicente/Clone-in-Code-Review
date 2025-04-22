			checkCallable();
			if (uri == null || uri.length() == 0)
				throw new IllegalArgumentException(
						JGitText.get().uriNotConfigured);
			if (inputStream == null) {
				if (path == null || path.length() == 0)
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				try {
					inputStream = new FileInputStream(path);
				} catch (IOException e) {
					throw new IllegalArgumentException(
							JGitText.get().pathNotConfigured);
				}
			}

			if (repo.isBare()) {
				bareProjects = new ArrayList<Project>();
				if (author == null)
					author = new PersonIdent(repo);
				if (callback == null)
					callback = new DefaultRemoteReader();
			} else
				git = new Git(repo);

			XmlManifest manifest = new XmlManifest(
					this
			try {
				manifest.read();
			} catch (IOException e) {
				throw new ManifestErrorException(e);
			}
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
			}
