		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent jobEvent) {
				IStatus result = jobEvent.getJob().getResult();
				if (result.getSeverity() == IStatus.CANCEL) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							try {
								MessageDialog
										.openInformation(
												getActiveShell(event),
												UIText.MergeAction_MergeCanceledTitle,
												UIText.MergeAction_MergeCanceledMessage);
							} catch (ExecutionException e) {
								Activator
										.handleError(
												UIText.MergeAction_MergeCanceledMessage,
												null, true);
							}
						}
					});
				} else if (!result.isOK()) {
					Activator.handleError(result.getMessage(), result
							.getException(), true);
				} else {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							try {
								MessageDialog.openInformation(
										getActiveShell(event),
										UIText.MergeAction_MergeResultTitle, op
												.getResult().toString());
							} catch (ExecutionException e) {
								Activator.handleError(
										op.getResult().toString(), null, true);
							}
						}
					});
				}
			}
		});
