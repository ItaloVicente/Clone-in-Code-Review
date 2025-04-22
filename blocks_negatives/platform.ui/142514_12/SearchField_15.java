					return Status.OK_STATUS;
				}
			};
			refreshQuickAccessContents.setRule(RESTORE_DIALOG_ENTRIES_SCHEDULING_RULE);
			refreshQuickAccessContents.setSystem(true);
			restoreDialogEntriesJob = Job.createSystem("Restore quick access elements", (IProgressMonitor monitor) -> { //$NON-NLS-1$
				if (txtQuickAccess.isDisposed()) {
					isLoadingPreviousElements = false;
					return;
				}
				try {
					restoreDialogEntries(dialogSettings, false, monitor);
				} catch (OperationCanceledException e) {
				} finally {
					refreshQuickAccessContents.schedule();
