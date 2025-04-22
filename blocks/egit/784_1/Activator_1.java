			Repository[] repos = org.eclipse.egit.core.Activator.getDefault()
					.getRepositoryCache().getAllReposiotries();
			if (repos.length == 0)
				return Status.OK_STATUS;
			monitor.beginTask(UIText.Activator_scanningRepositories,
					repos.length);
