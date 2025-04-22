	static class RIRefresh extends Job implements RepositoryListener {

		RIRefresh() {
			super(UIText.Activator_refreshJobName);
		}

		private Set<IProject> projectsToScan = new LinkedHashSet<IProject>();

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			monitor.beginTask(UIText.Activator_refreshingProjects, projects.length);

			while (projectsToScan.size() > 0) {
				IProject p;
				synchronized (projectsToScan) {
					if (projectsToScan.size() == 0)
						break;
					Iterator<IProject> i = projectsToScan.iterator();
					p = i.next();
					i.remove();
				}
				ISchedulingRule rule = p.getWorkspace().getRuleFactory().refreshRule(p);
				try {
					getJobManager().beginRule(rule, monitor);
					p.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1));
				} catch (CoreException e) {
					handleError(UIText.Activator_refreshFailed, e, false);
					return new Status(IStatus.ERROR, getPluginId(), e.getMessage());
				} finally {
					getJobManager().endRule(rule);
				}
			}
			monitor.done();
			return Status.OK_STATUS;
		}

		public void indexChanged(IndexChangedEvent e) {
			boolean autoRefresh = new InstanceScope().getNode(
					ResourcesPlugin.getPlugin().getBundle().getSymbolicName())
					.getBoolean(ResourcesPlugin.PREF_AUTO_REFRESH, false);
			if (!autoRefresh)
				return;

			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			Set<IProject> toRefresh= new HashSet<IProject>();
			for (IProject p : projects) {
				RepositoryMapping mapping = RepositoryMapping.getMapping(p);
				if (mapping != null && mapping.getRepository() == e.getRepository()) {
					toRefresh.add(p);
				}
			}
			synchronized (projectsToScan) {
				projectsToScan.addAll(toRefresh);
			}
			if (projectsToScan.size() > 0)
				schedule();
		}

		public void refsChanged(RefsChangedEvent e) {
		}

	}
