		if (!anyUri)
			setErrorMessage(UIText.SimpleConfigurePushDialog_MissingUriMessage);

		if (anyFetchUri)
			commonUriText.setText(config.getURIs().get(0).toPrivateString());
		else
