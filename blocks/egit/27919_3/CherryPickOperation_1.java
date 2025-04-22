					Git git = new Git(repo);
					ObjectId headCommitId = repo.resolve(Constants.HEAD);
					RevCommit headCommit = new RevWalk(repo)
							.parseCommit(headCommitId);
					git.rebase().setUpstream(headCommit.getParent(0))
							.runInteractively(handler)
							.setOperation(RebaseCommand.Operation.BEGIN).call();
