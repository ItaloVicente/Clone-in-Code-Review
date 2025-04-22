				boolean trace = GitTraceLocation.REPOSITORYCHANGESCANNER
						.isActive();
				final boolean[] shellActive = new boolean[] { false };
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						shellActive[0] = Display.getCurrent().getActiveShell() != null;
					}
				});
				if (!shellActive[0]) {
					if (trace)
						GitTraceLocation
								.getTrace()
								.trace(
										GitTraceLocation.REPOSITORYCHANGESCANNER
												.getLocation(),
										"No active shell, skipping " + getName() + " job"); //$NON-NLS-1$ //$NON-NLS-2$
					return Status.OK_STATUS;
				}
				Repository[] repos = org.eclipse.egit.core.Activator
						.getDefault().getRepositoryCache().getAllReposiotries();
				if (repos.length == 0)
					return Status.OK_STATUS;
				monitor.beginTask(UIText.Activator_scanningRepositories,
						repos.length);
				try {
					for (Repository repo : repos) {
						if (monitor.isCanceled())
							break;
						if (trace)
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.REPOSITORYCHANGESCANNER
											.getLocation(),
									"Scanning " + repo + " for changes"); //$NON-NLS-1$ //$NON-NLS-2$

						repo.scanForRepoChanges();
						monitor.worked(1);
					}
				} catch (IOException e) {
					if (trace)
