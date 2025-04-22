
		Job cleanup = Job.create(WorkbenchMessages.BundleSigningTray_Unget_Signing_Service, (IJobFunction) monitor -> {
			try {
				Job.getJobManager().join(signerJob, monitor);
			} catch (OperationCanceledException | InterruptedException e2) {
			}
			bundleContext.ungetService(factoryRef);
			return Status.OK_STATUS;
		});
		cleanup.setSystem(true);
		cleanup.schedule();

