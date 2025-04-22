		final IWorkbenchWizard[] workbenchWizard = new IWorkbenchWizard[1];
		final IStatus statuses[] = new IStatus[1];
		BusyIndicator.showWhile(parentWizardPage.getShell().getDisplay(), () -> SafeRunner.run(new SafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				IPluginContribution contribution = Adapters.adapt(wizardElement, IPluginContribution.class);
				statuses[0] = new Status(IStatus.ERROR,
						contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH, IStatus.OK,
						WorkbenchMessages.WorkbenchWizard_errorMessage, e);
			}

			@Override
			public void run() {
				try {
					workbenchWizard[0] = createWizard();
				} catch (CoreException e) {
					IPluginContribution contribution = Adapters.adapt(wizardElement, IPluginContribution.class);
					statuses[0] = new Status(IStatus.ERROR,
							contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH,
							IStatus.OK, WorkbenchMessages.WorkbenchWizard_errorMessage, e);
				}
			}
		}));

		if (statuses[0] != null) {
			parentWizardPage.setErrorMessage(WorkbenchMessages.WorkbenchWizard_errorMessage);
