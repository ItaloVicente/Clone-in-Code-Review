		List<URIish> result = new ArrayList<URIish>();
		if (pushURI != null)
			result.add(pushURI);
		return Collections.unmodifiableList(result);
	}

	public URIish getPushUri() {
		return pushURI;
