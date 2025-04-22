						return rw.parseCommit(commitId);
					} catch (IOException e) {
						throw new ManifestErrorException(e);
					}
				} else {
					try (Git git = new Git(repo)) {
						for (RepoProject proj : parser.getFilteredProjects()) {
							addSubmodule(proj.getUrl()
									proj.getPath()
									proj.getRevision()
									proj.getCopyFiles()
									proj.getLinkFiles()
									git);
						}
						return git.commit()
								.setMessage(RepoText.get().repoCommitMessage)
								.call();
					}
