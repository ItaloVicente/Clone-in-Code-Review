		Job job = Job.create(WorkbenchMessages.AboutSystemPage_FetchJobTitle, new IJobFunction() {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				final String info = ConfigurationInfo.getSystemSummary();
				if (!text.isDisposed()) {
					text.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!text.isDisposed()) {
								text.setText(info);
							}
						}
					});
				}
				return Status.OK_STATUS;
