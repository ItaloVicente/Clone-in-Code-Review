		if (dialog.shouldStartPushWizard()) {
			tagJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent jobChangeEvent) {
					if (jobChangeEvent.getResult().isOK()) {
						PushTagsWizard.openWizardDialog(repo, tagName);
					}
				}
			});
		}

