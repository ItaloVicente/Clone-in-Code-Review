		Job job = Job.create(WorkbenchMessages.AboutSystemPage_FetchJobTitle, (IJobFunction) monitor -> {
			final String info = ConfigurationInfo.getSystemSummary();
			if (!text.isDisposed()) {
				text.getDisplay().asyncExec(() -> {
					if (!text.isDisposed()) {
						text.setText(info);
					}
				});
