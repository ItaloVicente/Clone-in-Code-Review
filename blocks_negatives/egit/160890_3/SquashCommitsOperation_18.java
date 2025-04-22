				};
				try (Git git = new Git(repository)) {
					RebaseCommand command = git.rebase()
							.setUpstream(commits.get(0).getParent(0))
							.runInteractively(handler)
							.setOperation(RebaseCommand.Operation.BEGIN);
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
					command.call();
				} catch (GitAPIException e) {
					throw new TeamException(e.getLocalizedMessage(),
							e.getCause());
