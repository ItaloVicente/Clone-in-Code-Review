		Job cleanup = Job.create(WorkbenchMessages.BundleSigningTray_Unget_Signing_Service, new IJobFunction() {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				try {
					Job.getJobManager().join(signerJob, monitor);
				} catch (OperationCanceledException e) {
				} catch (InterruptedException e) {
				}
				bundleContext.ungetService(factoryRef);
				return Status.OK_STATUS;
