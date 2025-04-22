		IWorkspaceRunnable action = actMonitor -> {
			SubMonitor progress = SubMonitor.convert(actMonitor, 2);
			try (Git git = new Git(repository)) {
				RebaseCommand cmd = git.rebase().setProgressMonitor(
						new EclipseGitProgressTransformer(
								progress.newChild(1)));
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					cmd.setStrategy(strategy);
				}
				if (handler != null) {
					cmd.runInteractively(handler, true);
				}
				if (operation == Operation.BEGIN) {
					cmd.setPreserveMerges(preserveMerges);
					result = cmd.setUpstream(ref.getName()).call();
				} else {
					result = cmd.setOperation(operation).call();
				}
			} catch (JGitInternalException | GitAPIException e) {
				throw new CoreException(Activator.error(e.getMessage(), e));
			} finally {
				if (refreshNeeded()) {
					ProjectUtil.refreshValidProjects(validProjects,
							progress.newChild(1));
