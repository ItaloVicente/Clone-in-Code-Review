		mySelection = getSelection(event);
		try {
			IWorkbenchPart part = getPart(event);
			String question = UIText.DiscardChangesAction_confirmActionMessage;
			ILaunchConfiguration launch = LaunchFinder
					.getRunningLaunchConfiguration(
							Arrays.asList(getRepositories()), null);
			if (launch != null) {
				question = MessageFormat.format(question,
						"\n\n" + MessageFormat.format( //$NON-NLS-1$
								UIText.LaunchFinder_RunningLaunchMessage,
								launch.getName()));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}
			boolean performAction = MessageDialog.openConfirm(getShell(event),
					UIText.DiscardChangesAction_confirmActionTitle, question);
			if (!performAction) {
				return null;
