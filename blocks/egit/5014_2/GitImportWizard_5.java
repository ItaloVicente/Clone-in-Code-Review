	private Repository getClonedRepository() {
		 try {
			return  org.eclipse.egit.core.Activator
				.getDefault().getRepositoryCache().lookupRepository(new File(cloneDestination.getDestinationFile(), Constants.DOT_GIT));
		} catch (IOException e) {
			Activator.error("Error looking up repository at " + cloneDestination.getDestinationFile(), e); //$NON-NLS-1$
			return null;
		}
	}


	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					importProjects(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			Activator
					.handleError(e.getCause().getMessage(), e.getCause(), true);
			return false;
		} catch (InterruptedException e) {
			Activator.handleError(
					UIText.GitCreateProjectViaWizardWizard_AbortedMessage, e,
					true);
			return false;
		}
		return true;
	}

