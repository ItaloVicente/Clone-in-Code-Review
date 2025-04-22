					try {
						newInput = getRepositoriesFromDirs(monitor);
					} catch (InterruptedException e) {
						return new Status(IStatus.ERROR, Activator
								.getPluginId(), e.getMessage(), e);
					}
