	public static boolean isSharedWithGit(@NonNull IResource resource) {
		IProject project = resource.getProject();
		if (!project.isAccessible()) {
			return false;
		}
		try {
			RepositoryProvider provider = lookupProviderProp(project);
			if (provider != null) {
				if (GitProvider.ID.equals(provider.getID())) {
					return true;
				} else {
					return false;
				}
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

	private static void initProviderAsynchronously(
			final @NonNull IProject project) {
		synchronized (mappingInProgress) {
			if (mappingInProgress.contains(project)) {
				return;
			}
			mappingInProgress.add(project);
		}
		Job job = new Job(NLS.bind(CoreText.ResourceUtil_mapProjectJob,
				project.getName())) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				RepositoryProvider.getProvider(project, GitProvider.ID);
				synchronized (mappingInProgress) {
					mappingInProgress.remove(project);
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.setRule(new ExclusiveRule());
		job.schedule();
	}

	private static class ExclusiveRule implements ISchedulingRule {

		@Override
		public boolean contains(ISchedulingRule rule) {
			return isConflicting(rule);
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return rule instanceof ExclusiveRule;
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
