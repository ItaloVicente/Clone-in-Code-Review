				setImages(contributionParameters.serviceLocator,
						contributionParameters.iconStyle);

				if (contributionParameters.helpContextId == null) {
					try {
						this.helpContextId = commandService
								.getHelpContextId(contributionParameters.commandId);
					} catch (NotDefinedException e) {
					}
				}
				IWorkbenchLocationService wls = contributionParameters.serviceLocator
						.getService(IWorkbenchLocationService.class);
				final IWorkbench workbench = wls.getWorkbench();
				if (workbench != null && helpContextId != null) {
					this.workbenchHelpSystem = workbench.getHelpSystem();
