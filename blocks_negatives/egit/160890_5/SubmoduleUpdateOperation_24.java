		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor pm) throws CoreException {
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
