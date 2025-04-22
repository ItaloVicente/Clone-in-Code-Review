		if (signCommit) {
			String signingKey = repository != null
					? new GpgConfig(repository.getConfig()).getSigningKey()
					: null;
			boolean signingKeyAvailable = checkSigningKey(signingKey,
					committerPersonIdent);
			if (!signingKeyAvailable) {
				MessageDialog.openWarning(getShell(),
						UIText.CommitMessageComponent_ErrorMissingSigningKey,
						UIText.CommitMessageComponent_ErrorNoSigningKeyFound);
				return false;
			}
		}
