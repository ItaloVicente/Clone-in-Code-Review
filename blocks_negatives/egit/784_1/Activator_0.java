				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				monitor.beginTask(UIText.Activator_scanningRepositories, projects.length);
				Set<Repository> scanned = new HashSet<Repository>();
				for (IProject p : projects) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(p);
					if (mapping != null) {
						Repository r = mapping.getRepository();
						if (!scanned.contains(r)) {
							if (monitor.isCanceled())
								break;
							if (GitTraceLocation.UI.isActive())
								GitTraceLocation.getTrace().trace(
										GitTraceLocation.UI.getLocation(),
							scanned.add(r);
							ISchedulingRule rule = p.getWorkspace().getRuleFactory().modifyRule(p);
							getJobManager().beginRule(rule, monitor);
							try {
								r.scanForRepoChanges();
							} finally {
								getJobManager().endRule(rule);
							}
						}
					}
