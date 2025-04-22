
	private void addRepository(File repositoryDir) {
		util.addConfiguredRepository(repositoryDir);
		if (doAutoShare()) {
			autoShareProjects(repositoryDir);
		}
	}

	private void autoShareProjects(File repositoryDir) {
		IPath workingDirPath = new Path(repositoryDir.getAbsolutePath())
				.removeLastSegments(1);
		Map<IProject, File> connections = new HashMap<IProject, File>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (IProject project : projects) {
			RepositoryProvider provider = RepositoryProvider
					.getProvider(project);
			if (provider != null)
				continue;

			IPath location = project.getLocation();
			if (location == null)
				continue;

			if (!workingDirPath.isPrefixOf(location))
				continue;

			RepositoryFinder f = new RepositoryFinder(project);
			try {
				Collection<RepositoryMapping> mappings = f
						.find(new NullProgressMonitor());
				if (mappings.size() == 1) {
					connections.put(project, repositoryDir);
				}
			} catch (CoreException e) {
				continue;
			}
		}
		if (!connections.isEmpty()) {
			ConnectProviderOperation operation = new ConnectProviderOperation(
					connections);
			JobUtil.scheduleUserJob(operation,
					CoreText.Activator_AutoShareJobName, JobFamilies.AUTO_SHARE);
		}
	}

	private boolean doAutoShare() {
		IEclipsePreferences d = DefaultScope.INSTANCE.getNode(Activator
				.getPluginId());
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
				.getPluginId());
		return p.getBoolean(GitCorePreferences.core_autoShareProjects,
				d.getBoolean(GitCorePreferences.core_autoShareProjects, true));
	}

