						try {
							GitProjectData.delete(project);
						} catch (IOException e) {
							MultiStatus status = new MultiStatus(
									Activator.getPluginId(), IStatus.ERROR,
									e.getMessage(), e);
							status.add(new Status(IStatus.ERROR, Activator
									.getPluginId(), ce.getMessage(), ce));
							throw new CoreException(status);
					}
