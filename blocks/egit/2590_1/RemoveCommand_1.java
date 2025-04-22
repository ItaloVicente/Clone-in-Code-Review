		else {
			if (!projectsToDelete.isEmpty()) {
				final boolean[] confirmedCanceled = new boolean[] { false,
						false };
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
				if (confirmedCanceled[1])
					return;
				removeProjects = confirmedCanceled[0];
			}
		}

