	public static boolean isSharedWithGit(@NonNull IResource resource) {
		IProject project = resource.getProject();
		if (!project.isAccessible()) {
			return false;
		}
		try {
			GitProvider provider = lookupProviderProp(project);
			if (provider != null) {
				return true;
			}
			String existingID = project
					.getPersistentProperty(PROVIDER_PROP_KEY);
			boolean isGitProvider = GitProvider.ID.equals(existingID);
			if (isGitProvider) {
				MappingJob.initProviderAsynchronously(project);
			}
			return isGitProvider;
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
			return false;
		}
	}

	private static class MappingJob extends Job {

		final static MappingJob INSTANCE = new MappingJob();

		HashSet<IProject> projects = new LinkedHashSet<>();

		public MappingJob() {
			super(CoreText.ResourceUtil_mapProjectJob);
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			HashSet<IProject> work;
			synchronized (projects) {
				work = new LinkedHashSet<>(projects);
			}

			for (IProject project : work) {
				if (monitor.isCanceled()) {
					break;
				}
				RepositoryProvider.getProvider(project, GitProvider.ID);
			}

			synchronized (projects) {
				if (monitor.isCanceled()) {
					projects.clear();
				} else {
					projects.removeAll(work);
				}
				if (!projects.isEmpty()) {
					schedule();
				}
			}
			return Status.OK_STATUS;
		}

		static void initProviderAsynchronously(@NonNull IProject project) {
			synchronized (INSTANCE.projects) {
				if (INSTANCE.projects.contains(project)) {
					return;
				}
				INSTANCE.projects.add(project);
			}
			INSTANCE.schedule();
		}
	}

	@Nullable
	final public static GitProvider getGitProvider(@NonNull IProject project) {
		if (!project.isAccessible()) {
			return null;
		}
		try {
			GitProvider provider = lookupProviderProp(project);
			if (provider != null) {
				return provider;
			}
			String existingID = project
					.getPersistentProperty(PROVIDER_PROP_KEY);
			boolean isGitProvider = GitProvider.ID.equals(existingID);
			if (isGitProvider) {
				MappingJob.initProviderAsynchronously(project);
			}
			return null;
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
		}
		return null;
	}

	@Nullable
	private static GitProvider lookupProviderProp(IProject project)
			throws CoreException {
		Object provider = project.getSessionProperty(PROVIDER_PROP_KEY);
		if (provider instanceof GitProvider) {
			return (GitProvider) provider;
		}
		return null;
