
		if (!toAdd.equals(pushURI)) {
			pushURI = toAdd;
			return true;
		}

		return false;
	}

	public void setPushURI(final URIish pushUri) {
		this.pushURI = pushUri;
