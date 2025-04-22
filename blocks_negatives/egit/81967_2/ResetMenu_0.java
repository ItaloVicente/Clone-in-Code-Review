			String question = UIText.ResetTargetSelectionDialog_ResetConfirmQuestion;
			ILaunchConfiguration launch = LaunchFinder
					.getRunningLaunchConfiguration(Collections.singleton(repo),
							null);
			if (launch != null) {
				question = MessageFormat.format(question,
								UIText.LaunchFinder_RunningLaunchMessage,
								launch.getName()));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}
			if (!MessageDialog.openQuestion(shell,
					UIText.ResetTargetSelectionDialog_ResetQuestion,
					question)) {
