			String question = UIText.DiscardChangesAction_confirmActionMessage;
			String launch = LaunchFinder
					.getRunningLaunchConfiguration(
							Arrays.asList(getRepositories()), null);
			if (launch != null) {
				question = MessageFormat.format(question,
								UIText.LaunchFinder_RunningLaunchMessage,
								launch));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}
			boolean performAction = openConfirmationDialog(event, question);
			if (!performAction) {
				return null;
			}
