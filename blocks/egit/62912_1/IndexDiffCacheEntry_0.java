					Job.getJobManager().beginRule(rule, monitor);
					try {
						IProject[] validOpenProjects = ProjectUtil
								.getValidOpenProjects(repository);
						ProjectUtil.refreshResources(validOpenProjects,
								monitor);
					} catch (CoreException e) {
						return Activator.error(e.getMessage(), e);
					}
					if (Activator.getDefault().isDebugging()) {
						final long refresh = System.currentTimeMillis();
						Activator.logInfo("Resources refresh took " //$NON-NLS-1$
								+ (refresh - start) + " ms for " //$NON-NLS-1$
								+ repositoryName);

					}
				} catch (OperationCanceledException e) {
					return Status.CANCEL_STATUS;
				} finally {
					Job.getJobManager().endRule(rule);
