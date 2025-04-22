				try (Git git = new Git(repository)) {
					RebaseCommand cmd = git.rebase().setProgressMonitor(
							new EclipseGitProgressTransformer(
									progress.newChild(1)));
					MergeStrategy strategy = Activator.getDefault()
							.getPreferredMergeStrategy();
					if (strategy != null) {
						cmd.setStrategy(strategy);
					}
