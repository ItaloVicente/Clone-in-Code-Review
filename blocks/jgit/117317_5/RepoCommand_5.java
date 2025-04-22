			try (Git git = new Git(repo)) {
				for (RepoProject proj : parser.getFilteredProjects()) {
					addSubmodule(proj.getUrl()
							proj.getRevision()
							proj.getLinkFiles()
				}
				return git.commit().setMessage(RepoText.get().repoCommitMessage)
						.call();
			} catch (GitAPIException | IOException e) {
				throw new ManifestErrorException(e);
			}
