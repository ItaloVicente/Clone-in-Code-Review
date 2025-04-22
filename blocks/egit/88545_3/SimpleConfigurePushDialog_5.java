		if (!anyUri) {
			setErrorMessage(UIText.AbstractConfigureRemoteDialog_MissingUriMessage);
		}
		if (anyFetchUri) {
			commonUriText
					.setText(getConfig().getURIs().get(0).toPrivateString());
		} else {
