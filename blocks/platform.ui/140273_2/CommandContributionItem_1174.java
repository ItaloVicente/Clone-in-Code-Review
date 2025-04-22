			}
			IWorkbenchLocationService wls = contributionParameters.serviceLocator
					.getService(IWorkbenchLocationService.class);
			final IWorkbench workbench = wls.getWorkbench();
			if (workbench != null && helpContextId != null) {
				this.workbenchHelpSystem = workbench.getHelpSystem();
			}
