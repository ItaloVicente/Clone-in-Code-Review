		if (remote.getURIs().isEmpty()) {
			IStatus error = Activator.createErrorStatus(NLS.bind(
					UIText.FetchConfiguredRemoteAction_NoUrisDefinedMessage,
					remote.getName()), null);
			throw new CoreException(error);
		}

