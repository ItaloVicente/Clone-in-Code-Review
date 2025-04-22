				autoStash();
				if (stopAfterInitialization
						|| !walk.isMergedInto(
								walk.parseCommit(repo.resolve(Constants.HEAD))
								upstreamCommit)) {
					org.eclipse.jgit.api.Status status = Git.wrap(repo)
							.status().call();
					if (status.hasUncommittedChanges()) {
						List<String> list = new ArrayList<String>();
						list.addAll(status.getUncommittedChanges());
						return RebaseResult.uncommittedChanges(list);
					}
				}
