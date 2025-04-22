			job.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					IStatus result = event.getJob().getResult();
					if (result.getSeverity() == IStatus.CANCEL) {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageDialog
										.openInformation(
												getShell(),
												UIText.MergeAction_MergeCanceledTitle,
												UIText.MergeAction_MergeCanceledMessage);
							}
						});
					} else if (!result.isOK()) {
						Activator.handleError(result.getMessage(), result
								.getException(), true);
					} else {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								MessageDialog.openInformation(getShell(),
										UIText.MergeAction_MergeResultTitle, op
												.getResult().toString());
							}
						});
					}
				}
			});
