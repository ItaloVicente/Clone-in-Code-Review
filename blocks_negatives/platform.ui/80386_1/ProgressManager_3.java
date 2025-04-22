				updateFor(event);
				if (event.getJob().isUser()) {
					boolean noDialog = shouldRunInBackground();
					if (!noDialog) {
						final IJobChangeEvent finalEvent = event;
						WorkbenchJob showJob = new WorkbenchJob(
								ProgressMessages.ProgressManager_showInDialogName) {
