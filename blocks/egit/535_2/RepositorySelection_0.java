	public List<URIish> getPushURIs() {
		if (isURISelected())
			return Collections.singletonList(uri);
		if (isConfigSelected())
			return config.getPushURIs();
		return null;
	}

