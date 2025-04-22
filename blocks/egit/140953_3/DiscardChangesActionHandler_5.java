			} else if (LaunchFinder.shouldCancelBecauseOfRunningLaunches(
					paths.keySet(), null)) {
				return null;
			}
			JobUtil.scheduleUserWorkspaceJob(operation,
					UIText.DiscardChangesAction_discardChanges,
					JobFamilies.DISCARD_CHANGES);
