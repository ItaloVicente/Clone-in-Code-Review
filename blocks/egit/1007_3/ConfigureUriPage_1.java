	public void setURI(URIish uri) {
		myUri = uri;
		uriText.setText(uri.toPrivateString());
		checkPage();
	}

