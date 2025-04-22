			Map<Repository, Collection<String>> paths = operation
					.getPathsPerRepository();
			if (haveChanges(paths)) {
				String question = UIText.DiscardChangesAction_confirmActionMessage;
				String launch = LaunchFinder
						.getRunningLaunchConfiguration(paths.keySet(), null);
				if (launch != null) {
					question = MessageFormat.format(question,
							"\n\n" + MessageFormat.format( //$NON-NLS-1$
									UIText.LaunchFinder_RunningLaunchMessage,
									launch));
				} else {
					question = MessageFormat.format(question, ""); //$NON-NLS-1$
