				getUISynchronize().syncExec(() -> {
					if (!ProgressManagerUtil.safeToOpen(ProgressMonitorJobsDialog.this, null)) {
						watchTicks();
						return;
					}

					if (!alreadyClosed) {
						open();
					}
				});
