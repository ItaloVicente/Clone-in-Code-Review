				if (stopAfterInitialization
						|| !walk.isMergedInto(
								walk.parseCommit(repo.resolve(Constants.HEAD)),
								upstreamCommit)) {
					org.eclipse.jgit.api.Status status = Git.wrap(repo)
							.status().setIgnoreSubmodules(IgnoreSubmoduleMode.ALL).call();
