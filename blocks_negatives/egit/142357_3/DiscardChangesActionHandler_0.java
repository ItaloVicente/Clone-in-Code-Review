			if (haveChanges(paths)) {
				String question = UIText.DiscardChangesAction_confirmActionMessage;
				String launch = LaunchFinder
						.getRunningLaunchConfiguration(paths.keySet(), null);
				if (launch != null) {
					question = MessageFormat.format(question,
									UIText.LaunchFinder_RunningLaunchMessage,
									launch));
				} else {
					question = MessageFormat.format(question, ""); //$NON-NLS-1$
				}
				if (!openConfirmationDialog(event, question)) {
					return null;
				}
			} else if (LaunchFinder.shouldCancelBecauseOfRunningLaunches(
					paths.keySet(), null)) {
