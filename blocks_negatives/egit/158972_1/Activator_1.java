	private void registerAutoShareProjects() {
		shareGitProjectsJob = new AutoShareProjects();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				shareGitProjectsJob, IResourceChangeEvent.POST_CHANGE);
	}

	private static class AutoShareProjects implements IResourceChangeListener {

		private static int INTERESTING_CHANGES = IResourceDelta.ADDED
				| IResourceDelta.OPEN;

		private final CheckProjectsToShare checkProjectsJob;

		public AutoShareProjects() {
			checkProjectsJob = new CheckProjectsToShare();
		}

		private boolean doAutoShare() {
			IEclipsePreferences d = DefaultScope.INSTANCE.getNode(Activator
					.getPluginId());
			IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
					.getPluginId());
			return p.getBoolean(GitCorePreferences.core_autoShareProjects, d
					.getBoolean(GitCorePreferences.core_autoShareProjects,
							true));
		}

		public void stop() {
			boolean isRunning = !checkProjectsJob.cancel();
			Job.getJobManager().cancel(JobFamilies.AUTO_SHARE);
			try {
				if (isRunning) {
					checkProjectsJob.join();
				}
				Job.getJobManager().join(JobFamilies.AUTO_SHARE,
						new NullProgressMonitor());
			} catch (OperationCanceledException e) {
			} catch (InterruptedException e) {
				logError(e.getLocalizedMessage(), e);
			}
		}

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			if (!doAutoShare()) {
				return;
			}
			try {
				final Set<IProject> projectCandidates = new LinkedHashSet<>();
				event.getDelta().accept(new IResourceDeltaVisitor() {
					@Override
					public boolean visit(IResourceDelta delta)
							throws CoreException {
						return collectOpenedProjects(delta,
								projectCandidates);
					}
				});
				if(!projectCandidates.isEmpty()){
					checkProjectsJob.addProjectsToCheck(projectCandidates);
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
				return;
			}
		}

		/*
		 * This method should not use RepositoryMapping.getMapping(project) or
		 * RepositoryProvider.getProvider(project) which can trigger
		 * RepositoryProvider.map(project) and deadlock current thread. See
		 */
		private boolean collectOpenedProjects(IResourceDelta delta,
				Set<IProject> projects) {
			if (delta.getKind() == IResourceDelta.CHANGED
					&& (delta.getFlags() & INTERESTING_CHANGES) == 0) {
				return true;
			}
			final IResource resource = delta.getResource();
			if (resource.getType() == IResource.ROOT) {
				return true;
			}
			if (resource.getType() != IResource.PROJECT) {
				return false;
			}
			if (!resource.isAccessible() || resource.getLocation() == null) {
				return false;
			}
			projects.add((IProject) resource);
			return false;
		}

	}
