	private void registerAutoShareProjects() {
		shareGitProjectsJob = new AutoShareProjects();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				shareGitProjectsJob, IResourceChangeEvent.POST_CHANGE);
	}

	private static class AutoShareProjects implements
			IResourceChangeListener {

		private static int INTERESTING_CHANGES = IResourceDelta.ADDED
				| IResourceDelta.OPEN;

		public AutoShareProjects() {
		}

		private boolean doAutoShare() {
			IEclipsePreferences d = new DefaultScope().getNode(Activator
					.getPluginId());
			IEclipsePreferences p = new InstanceScope().getNode(Activator
					.getPluginId());
			return p.getBoolean(GitCorePreferences.core_autoShareProjects, d
					.getBoolean(GitCorePreferences.core_autoShareProjects,
							false));
		}

		public void resourceChanged(IResourceChangeEvent event) {
			try {
				event.getDelta().accept(new IResourceDeltaVisitor() {

					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if (!doAutoShare())
							return false;
						if (delta.getKind() == IResourceDelta.CHANGED
								&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
							return true;
						final IResource resource = delta.getResource();
						if (!resource.exists())
							return false;
						if (resource.getType() != IResource.PROJECT)
							return true;
						if (Team.isIgnoredHint(resource))
							return false;
						if (RepositoryMapping.getMapping(resource) != null)
							return false;
						final IProject project = (IProject) resource;
						RepositoryProvider provider = RepositoryProvider
								.getProvider(project);
						if (provider != null)
							return false;
						RepositoryFinder f = new RepositoryFinder(project);
						Collection<RepositoryMapping> mappings = f.find(new NullProgressMonitor());
						try {
							if (mappings.size() == 1) {
								RepositoryMapping m = mappings.iterator()
										.next();
								final File repositoryDir = m
										.getGitDirAbsolutePath().toFile();
								final ConnectProviderOperation op = new ConnectProviderOperation(
										project, repositoryDir);
								JobUtil.scheduleUserJob(op,
										CoreText.Activator_AutoShareJobName,
										JobFamilies.AUTO_SHARE);
								Activator.getDefault().getRepositoryUtil()
										.addConfiguredRepository(repositoryDir);
							}
						} catch (IllegalArgumentException e) {
							logError(CoreText.Activator_AutoSharingFailed, e);
						}
						return false;
					}
				});
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
				return;
			}
		}
	}
