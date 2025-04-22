
					List<IProject> projectsToDelete = new ArrayList<IProject>();
					File workDir = repo.getWorkDir();
					final IPath wdPath = new Path(workDir.getAbsolutePath());
					for (IProject prj : ResourcesPlugin.getWorkspace()
							.getRoot().getProjects()) {
						if (wdPath.isPrefixOf(prj.getLocation())) {
							projectsToDelete.add(prj);
						}
					}

					if (!projectsToDelete.isEmpty()) {
						boolean confirmed;
						confirmed = confirmProjectDeletion(projectsToDelete);
						if (!confirmed) {
							return;
						}
					}

					IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

						public void run(IProgressMonitor monitor)
								throws CoreException {

							for (IProject prj : ResourcesPlugin.getWorkspace()
									.getRoot().getProjects()) {
								if (wdPath.isPrefixOf(prj.getLocation())) {
									prj.delete(false, false, monitor);
								}
							}

							removeDir(repo.getDirectory());
							scheduleRefresh();
						}
					};

					try {
						ResourcesPlugin.getWorkspace().run(wsr,
								ResourcesPlugin.getWorkspace().getRoot(),
								IWorkspace.AVOID_UPDATE,
								new NullProgressMonitor());
					} catch (CoreException e1) {
						Activator.logError(e1.getMessage(), e1);
					}

