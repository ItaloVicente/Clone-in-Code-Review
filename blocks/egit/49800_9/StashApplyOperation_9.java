					StashApplyCommand command = Git.wrap(repository)
							.stashApply().setStashRef(commit.name());
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						command.setStrategy(strategy);
					}
					command.call();
