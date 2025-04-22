		IWorkspaceRunnable action = pm -> {
			SubMonitor progress = SubMonitor.convert(pm, 3);
			try {
				IProject[] validProjects = ProjectUtil
						.getValidOpenProjects(repository);
				progress.worked(1);
				StashApplyCommand command = Git.wrap(repository)
						.stashApply().setStashRef(commit.name());
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					command.setStrategy(strategy);
