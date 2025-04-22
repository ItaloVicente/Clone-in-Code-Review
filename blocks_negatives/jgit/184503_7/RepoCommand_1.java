		try (Git git = new Git(repo)) {
			for (RepoProject proj : filteredProjects) {
				addSubmodule(proj.getName(), proj.getUrl(), proj.getPath(),
						proj.getRevision(), proj.getCopyFiles(),
						proj.getLinkFiles(), git);
			}
			return git.commit().setMessage(RepoText.get().repoCommitMessage)
					.call();
		} catch (IOException e) {
			throw new ManifestErrorException(e);
		}
	}
