				};
				try (Git git = new Git(repository)) {
					git.rebase().setUpstream(commit.getParent(0))
							.runInteractively(handler)
							.setOperation(RebaseCommand.Operation.BEGIN).call();
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
