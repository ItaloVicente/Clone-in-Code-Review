		try (Git git = new Git(repo)) {
			for (RepoProject proj : filteredProjects) {
				addSubmodule(proj.getName(), proj.getUrl(), proj.getPath(),
						proj.getRevision(), proj.getCopyFiles(),
						proj.getLinkFiles(), git);
			}
			return git.commit().setMessage(RepoText.get().repoCommitMessage)
					.call();
		} catch (GitAPIException | IOException e) {
			throw new ManifestErrorException(e);
		}
	}

	private void prepareIndex(List<RepoProject> projects, DirCache index,
			ObjectInserter inserter) throws IOException, GitAPIException {
		Config cfg = new Config();
		StringBuilder attributes = new StringBuilder();
		DirCacheBuilder builder = index.builder();
		for (RepoProject proj : projects) {
			String name = proj.getName();
			String path = proj.getPath();
			String url = proj.getUrl();
			ObjectId objectId;
			if (ObjectId.isId(proj.getRevision())) {
				objectId = ObjectId.fromString(proj.getRevision());
			} else {
				objectId = callback.sha1(url, proj.getRevision());
				if (objectId == null && !ignoreRemoteFailures) {
					throw new RemoteUnavailableException(url);
				}
				if (recordRemoteBranch) {
					cfg.setString("submodule", name, field, //$NON-NLS-1$
							proj.getRevision());
				}
