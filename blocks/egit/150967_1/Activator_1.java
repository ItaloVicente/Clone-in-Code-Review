	private static class ResourceRefreshJob extends Job {

		ResourceRefreshJob() {
			super(UIText.Activator_refreshJobName);
			setUser(false);
			setSystem(true);
		}

		private static class WorkingTreeChanges {

			private final File workTree;

			private final Set<String> modified;

			private final Set<String> deleted;

			public WorkingTreeChanges(WorkingTreeModifiedEvent event) {
				workTree = event.getRepository().getWorkTree()
						.getAbsoluteFile();
				modified = new HashSet<>(event.getModified());
				deleted = new HashSet<>(event.getDeleted());
			}

			public File getWorkTree() {
				return workTree;
			}

			public Set<String> getModified() {
				return modified;
			}

			public Set<String> getDeleted() {
				return deleted;
			}

			public boolean isEmpty() {
				return modified.isEmpty() && deleted.isEmpty();
			}

			public WorkingTreeChanges merge(WorkingTreeModifiedEvent event) {
				modified.removeAll(event.getDeleted());
				deleted.removeAll(event.getModified());
				modified.addAll(event.getModified());
				deleted.addAll(event.getDeleted());
				return this;
			}
		}

		private Map<File, WorkingTreeChanges> repositoriesChanged = new LinkedHashMap<>();

		@Override
		public IStatus run(IProgressMonitor monitor) {
			try {
				List<WorkingTreeChanges> changes;
				synchronized (repositoriesChanged) {
					if (repositoriesChanged.isEmpty()) {
						return Status.OK_STATUS;
					}
					changes = new ArrayList<>(repositoriesChanged.values());
					repositoriesChanged.clear();
				}

				SubMonitor progress = SubMonitor.convert(monitor,
						changes.size());
				try {
					for (WorkingTreeChanges change : changes) {
						if (progress.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						ResourceRefreshHandler handler = new ResourceRefreshHandler();
						handler.refreshRepository(new WorkingTreeModifiedEvent(
								change.getModified(), change.getDeleted()),
								change.getWorkTree(), progress.newChild(1));
					}
				} catch (OperationCanceledException oe) {
					return Status.CANCEL_STATUS;
				} catch (CoreException e) {
					handleError(UIText.Activator_refreshFailed, e, false);
					return new Status(IStatus.ERROR, getPluginId(),
							e.getMessage());
				}

				if (!monitor.isCanceled()) {
					synchronized (repositoriesChanged) {
						if (!repositoriesChanged.isEmpty()) {
							schedule(100);
						}
					}
				}
			} finally {
				monitor.done();
			}
			return Status.OK_STATUS;
		}

		public void trigger(Collection<WorkingTreeModifiedEvent> events) {
			boolean haveChanges = false;
			for (WorkingTreeModifiedEvent event : events) {
				if (event.isEmpty()) {
					continue;
				}
				Repository repo = event.getRepository();
				if (repo == null || repo.isBare()) {
					continue; // Should never occur
				}
				File gitDir = repo.getDirectory();
				synchronized (repositoriesChanged) {
					WorkingTreeChanges changes = repositoriesChanged
							.get(gitDir);
					if (changes == null) {
						repositoriesChanged.put(gitDir,
								new WorkingTreeChanges(event));
					} else {
						changes.merge(event);
						if (changes.isEmpty()) {
							repositoriesChanged.remove(gitDir);
						}
					}
				}
				haveChanges = true;
			}
			if (haveChanges && (!Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.REFRESH_ONLY_WHEN_ACTIVE)
					|| isActive())) {
				schedule();
			}
		}
	}

