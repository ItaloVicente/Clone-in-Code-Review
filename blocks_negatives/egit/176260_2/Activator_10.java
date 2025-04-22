	/**
	 * Obtains the {@link IProxyService}.
	 *
	 * @return the {@link IProxyService} or {@code null} if none is available.
	 */
	public IProxyService getProxyService() {
		return proxyServiceTracker.getService();
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		SshPreferencesMirror.INSTANCE.stop();
		if (preferenceChangeListener != null) {
			InstanceScope.INSTANCE.getNode(PLUGIN_ID)
					.removePreferenceChangeListener(preferenceChangeListener);
			preferenceChangeListener = null;
		}
		SshSessionFactory current = SshSessionFactory.getInstance();
		if (current instanceof SshdSessionFactory) {
			((SshdSessionFactory) current).close();
		}
		if (proxyServiceTracker != null) {
			proxyServiceTracker.close();
			proxyServiceTracker = null;
		}
		if (mergeStrategyRegistryListener != null) {
			Platform.getExtensionRegistry()
					.removeListener(mergeStrategyRegistryListener);
			mergeStrategyRegistryListener = null;
		}
		if (preDeleteProjectListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(preDeleteProjectListener);
			preDeleteProjectListener = null;
		}
		if (ignoreDerivedResourcesListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					ignoreDerivedResourcesListener);
			ignoreDerivedResourcesListener.stop();
			ignoreDerivedResourcesListener = null;
		}
		if (refreshHandle != null) {
			refreshHandle.remove();
			refreshHandle = null;
		}
		if (shareGitProjectsJob != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(
					shareGitProjectsJob);
			shareGitProjectsJob.stop();
			shareGitProjectsJob = null;
		}
		GitProjectData.detachFromWorkspace();
		indexDiffCache.dispose();
		indexDiffCache = null;
		repositoryCache.clear();
		repositoryCache = null;
		repositoryUtil.dispose();
		repositoryUtil = null;
		secureStore = null;
		Config.setTypedConfigGetter(null);
		super.stop(context);
		plugin = null;
	}

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
					.PLUGIN_ID);
			IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
					.PLUGIN_ID);
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

	private static class CheckProjectsToShare extends Job {
		private Object lock = new Object();

		private Set<IProject> projectCandidates;

		public CheckProjectsToShare() {
			super(CoreText.Activator_AutoShareJobName);
			this.projectCandidates = new LinkedHashSet<>();
			setUser(false);
			setSystem(true);
		}

		public void addProjectsToCheck(Set<IProject> projects) {
			synchronized (lock) {
				this.projectCandidates.addAll(projects);
				if (!projectCandidates.isEmpty()) {
					schedule(100);
				}
			}
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			Set<IProject> projectsToCheck;
			synchronized (lock) {
				projectsToCheck = projectCandidates;
				projectCandidates = new LinkedHashSet<>();
			}
			if (projectsToCheck.isEmpty()) {
				return Status.OK_STATUS;
			}

			final Map<IProject, File> projects = new HashMap<>();
			for (IProject project : projectsToCheck) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				if (project.isAccessible()) {
					try {
						visitConnect(project, projects);
					} catch (CoreException e) {
						logError(e.getMessage(), e);
					}
				}
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			if (projects.size() > 0) {
				ConnectProviderOperation op = new ConnectProviderOperation(
						projects);
				op.setRefreshResources(false);
				JobUtil.scheduleUserJob(op,
						CoreText.Activator_AutoShareJobName,
						JobFamilies.AUTO_SHARE);
			}
			return Status.OK_STATUS;
		}

		private void visitConnect(IProject project,
				final Map<IProject, File> projects) throws CoreException {

			if (RepositoryMapping.getMapping(project) != null) {
				return;
			}
			RepositoryProvider provider = RepositoryProvider
					.getProvider(project);
			if (provider != null) {
				return;
			}
			RepositoryFinder f = new RepositoryFinder(project);
			f.setFindInChildren(false);
			List<RepositoryMapping> mappings = f
					.find(new NullProgressMonitor());
			if (mappings.isEmpty()) {
				return;
			}
			RepositoryMapping m = mappings.get(0);
			IPath gitDirPath = m.getGitDirAbsolutePath();
			if (gitDirPath == null || !isValidRepositoryPath(gitDirPath)) {
				return;
			}

			File repositoryDir = gitDirPath.toFile();
			projects.put(project, repositoryDir);

			Set<String> configured = Activator.getDefault().getRepositoryUtil()
					.getRepositories();
			if (configured.contains(gitDirPath.toString())) {
				return;
			}
			int nofMappings = mappings.size();
			if (nofMappings > 1) {
				IPath lastPath = gitDirPath;
				for (int i = 1; i < nofMappings; i++) {
					IPath nextPath = mappings.get(i).getGitDirAbsolutePath();
					if (nextPath == null) {
						continue;
					}
					if (configured.contains(nextPath.toString())) {
						return;
					} else if (!isValidRepositoryPath(nextPath)) {
						break;
					}
					lastPath = nextPath;
				}
				repositoryDir = lastPath.toFile();
			}
			try {
				Activator.getDefault().getRepositoryUtil()
						.addConfiguredRepository(repositoryDir);
			} catch (IllegalArgumentException e) {
				logError(CoreText.Activator_AutoSharingFailed, e);
			}
		}
	}

	private static boolean isValidRepositoryPath(@NonNull IPath gitDirPath) {
		if (gitDirPath.segmentCount() == 0) {
			return false;
		}
		IPath workingDir = gitDirPath.removeLastSegments(1);
		if (workingDir.isRoot()) {
			return false;
		}
		File userHome = FS.DETECTED.userHome();
		if (userHome != null) {
			Path userHomePath = new Path(userHome.getAbsolutePath());
			if (workingDir.isPrefixOf(userHomePath)) {
				return false;
			}
		}
		return true;
	}

	private void registerAutoIgnoreDerivedResources() {
		ignoreDerivedResourcesListener = new IgnoreDerivedResources();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				ignoreDerivedResourcesListener,
				IResourceChangeEvent.POST_CHANGE);
	}

	/**
	 * @return true if the derived resources should be automatically added to
	 *         the .gitignore files
	 */
	public static boolean autoIgnoreDerived() {
		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		return p.getBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
				d.getBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
						true));
	}

