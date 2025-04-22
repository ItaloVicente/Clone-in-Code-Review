		IWorkspaceRunnable action = pm -> {
			RepositoryUtil util = Activator.getDefault()
					.getRepositoryUtil();
			SubMonitor progress = SubMonitor.convert(pm, 4);
			progress.setTaskName(MessageFormat.format(
					CoreText.SubmoduleUpdateOperation_updating,
					util.getRepositoryName(repository)));

			Git git = Git.wrap(repository);

			Collection<String> updated = null;
			try {
				SubmoduleInitCommand init = git.submoduleInit();
				for (String path : paths)
					init.addPath(path);
				init.call();
				progress.worked(1);

				SubmoduleUpdateCommand update = git.submoduleUpdate();
				for (String path : paths)
					update.addPath(path);
				update.setProgressMonitor(new EclipseGitProgressTransformer(
						progress.newChild(2)));
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					update.setStrategy(strategy);
				}
				update.setCallback(new CloneCommand.Callback() {
