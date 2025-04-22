			String question = UIText.DiscardChangesAction_confirmActionMessage;
			String launch = LaunchFinder
					.getRunningLaunchConfiguration(
							Collections.singleton(getCurrentRepository()),
							null);
			if (launch != null) {
				question = MessageFormat.format(question,
								UIText.LaunchFinder_RunningLaunchMessage,
								launch));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}

			MessageDialog dlg = new MessageDialog(form.getShell(),
					UIText.DiscardChangesAction_confirmActionTitle, null,
					question, MessageDialog.CONFIRM,
					new String[] {
							UIText.DiscardChangesAction_discardChangesButtonText,
							IDialogConstants.CANCEL_LABEL },
					0);
			if (dlg.open() != Window.OK) {
