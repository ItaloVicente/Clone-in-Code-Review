	public URIish getURI(boolean pushMode) {
		if (isConfigSelected())
			if (pushMode) {
				if (config.getPushURIs().size() > 0)
					return config.getPushURIs().get(0);
				else if (config.getURIs().size() > 0)
					return config.getURIs().get(0);
				else
					return null;
			} else {
				if (config.getURIs().size() > 0)
					return config.getURIs().get(0);
				else if (config.getPushURIs().size() > 0)
					return config.getPushURIs().get(0);
				else
					return null;
			}
		return uri;
	}

