	private Repository getClonedRepository() {
		 try {
			return  org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache().lookupRepository(new File(cloneDestination.getDestinationFile(), Constants.DOT_GIT));
		} catch (IOException e) {
			Activator.error("Error looking up repository at " + cloneDestination.getDestinationFile(), e); //$NON-NLS-1$
			return null;
		}
