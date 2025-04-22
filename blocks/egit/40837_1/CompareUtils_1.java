				CompareEditorInput in;
				try {
					in = prepareCompareInput(repository, gitPath, base, refName);
				} catch (IOException e) {
					return Activator.createErrorStatus(
							UIText.CompareWithRefAction_errorOnSynchronize, e);
				}

				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				openCompareEditorRunnable(page, in);
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}
