	private boolean doAutoShare() {
		IEclipsePreferences d = DefaultScope.INSTANCE.getNode(Activator
				.getPluginId());
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
				.getPluginId());
		return p.getBoolean(GitCorePreferences.core_autoShareProjects,
				d.getBoolean(GitCorePreferences.core_autoShareProjects, true));
	}

	private void autoShareProjects(Repository repoToCreate, IProject[] projects) {
		final Map<IProject, File> projectsMap = new HashMap<IProject, File>();
		for (IProject project : projects)
			projectsMap.put(project, repoToCreate.getDirectory());
		ConnectProviderOperation op = new ConnectProviderOperation(projectsMap);
		JobUtil.scheduleUserJob(op, CoreText.Activator_AutoShareJobName,
				JobFamilies.AUTO_SHARE);
	}

