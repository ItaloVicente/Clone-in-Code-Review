				if (stopAfterInitialization
						|| !walk.isMergedInto(
								walk.parseCommit(repo.resolve(Constants.HEAD))
								upstreamCommit)) {
					org.eclipse.jgit.api.Status status = Git.wrap(repo)
							.status().call();
					if (status.hasUncommittedChanges()) {
						if (!stopAfterInitialization)
							return new RebaseResult(
									status.getUncommittedChanges());
					}
				}
