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
				initProviderAsynchronously(project);
			}
			return isGitProvider;
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(e.getStatus());
			return false;
		}
	}

	private static void initProviderAsynchronously(@NonNull IProject project) {
		synchronized (mappingJob.projects) {
			if (mappingJob.projects.contains(project)) {
				return;
			}
			mappingJob.projects.add(project);
		}
		mappingJob.schedule();
	}

	private static class MappingJob extends Job {
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
				initProviderAsynchronously(project);
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
