
				final List<RepositoryTreeNode<Repository>> input;
				try {
					input = getRepositoriesFromDirs(monitor);
				} catch (InterruptedException e) {
					return new Status(IStatus.ERROR, Activator.getPluginId(), e
							.getMessage(), e);
				}
