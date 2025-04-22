						RepositoryMatch match = repos.get(commit
								.getRepository());
						if (match == null) {
							match = new RepositoryMatch(commit.getRepository());
							repos.put(commit.getRepository(), match);
						}
						match.addCommit(commit);
