				return org.eclipse.egit.core.Activator
						.getDefault()
						.getRepositoryCache()
						.lookupRepository(
								new File(cloneDestination.getDestinationFile(),
										Constants.DOT_GIT));
