					try (RevWalk walk = new RevWalk(repository)) {
						walk.setRetainBody(true);
						RevCommit commit = walk.parseCommit(commitId);
						contentProvider.add(
								new RepositoryCommit(repository, commit),
								itemsFilter);
					}
