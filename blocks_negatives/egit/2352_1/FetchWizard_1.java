		try {
			if (repoSelection.isConfigSelected())
				transport = Transport.open(localDb, repoSelection.getConfig());
			else
				transport = Transport.open(localDb, repoSelection.getURI(false));
		} catch (final NotSupportedException e) {
			ErrorDialog.openError(getShell(),
					UIText.FetchWizard_transportNotSupportedTitle,
					UIText.FetchWizard_transportNotSupportedMessage,
					new Status(IStatus.ERROR, org.eclipse.egit.ui.Activator
							.getPluginId(), e.getMessage(), e));
			return false;
		}
