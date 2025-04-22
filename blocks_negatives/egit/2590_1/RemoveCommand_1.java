				final boolean[] confirmedCanceled = new boolean[] { false,
						false };

				if (!projectsToDelete.isEmpty()) {
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							try {
								confirmedCanceled[0] = confirmProjectDeletion(
										projectsToDelete, event);
							} catch (OperationCanceledException e) {
								confirmedCanceled[1] = true;
							}
						}
					});
				}
				if (confirmedCanceled[1]) {
					return Status.OK_STATUS;
				}
				if (confirmedCanceled[0]) {
