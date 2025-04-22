					public void run() {
						try {
							confirmedCanceled[0] = confirmProjectDeletion(
									projectsToDelete, event);
						} catch (OperationCanceledException e) {
							confirmedCanceled[1] = true;
						}
